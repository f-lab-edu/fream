package kr.flab.fream.controller.auction

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import kr.flab.domain.product.ProductFixtures
import kr.flab.fream.auth.AbstractAuthenticationResolver
import kr.flab.fream.config.FormattingConfiguration
import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.config.WebConfig
import kr.flab.fream.controller.product.ProductDto
import kr.flab.fream.domain.auction.dto.SignAuctionResponse
import kr.flab.fream.domain.auction.model.AuctionType
import kr.flab.fream.domain.auction.service.AuctionService
import kr.flab.fream.domain.user.model.User
import org.modelmapper.ModelMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import spock.lang.Specification

import java.nio.charset.Charset
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(AuctionController)
@Import([ObjectMapper, ModelMapperConfiguration, FormattingConfiguration, WebConfig])
class AuctionControllerSpec extends Specification {

    static final String ASK_URL = "/auction/asks"

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    ModelMapper modelMapper

    @SpringBean
    AuctionService auctionService = Stub()

    @SpringBean(name = "temporaryAuthenticationResolver")
    AbstractAuthenticationResolver authenticationResolver = Stub()

    @SuppressWarnings("unused")
    // IntelliJ 가 spock setup 을 인식하지 못함
    void setup() {
        objectMapper.registerModule(new JavaTimeModule())
    }

    def "return 400 invalid AuctionRequest '#testcase'"() {
        given:
        def resultActions = mockMvc.perform(post(ASK_URL).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))

        expect:
        resultActions.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))

        where:
        requestBody << [
            new AuctionRequest(new BigDecimal(-1), 1, 1, 1, 1, AuctionType.ASK),
            new AuctionRequest(null, 1, 1, 1, 1, AuctionType.ASK),
            new AuctionRequest(new BigDecimal(100_000), null, 1, 1, 1, AuctionType.ASK),
            new AuctionRequest(new BigDecimal(100_000), 1, null, 1, 1, AuctionType.ASK),
            new AuctionRequest(new BigDecimal(100_000), 1, 1, null, 1, AuctionType.ASK),
            new AuctionRequest(new BigDecimal(100_000), 1, 1, 1, 0, AuctionType.ASK),
            new AuctionRequest(new BigDecimal(100_000), 1, 1, 1, null, AuctionType.ASK),
            new AuctionRequest(new BigDecimal(100_000), 1, 1, 1, null, null),
        ]
        testcase << [
            "price is less than 0",
            "null price",
            "null productId",
            "null sizeId",
            "null userId",
            "duyDays is less than 1",
            "null duyDays",
            "null auctionType"
        ]
    }

    def "return 200 with valid creating '#type' request"() {
        given:
        def auctionRequest = new AuctionRequest(new BigDecimal(100_000), 1, 1, 1, 1, type)
        def auction = type.constructor.apply(auctionRequest)
        def product = ProductFixtures.getNikeDunkLowRetroBlack()
        def size = product.sizes.sizeList.get(0)
        auction.setUser(new User(1))
        auction.setProduct(product)
        auction.setSize(size)

        auctionService.createAuction(_ as AuctionRequest) >> modelMapper.map(auction, AuctionDto.getTypeObject())

        when:
        def resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(auctionRequest)))

        then:
        def result = resultActions.andExpect(status().is(HttpStatus.CREATED.value()))
            .andReturn()

        and:
        AuctionDto auctionDto = new AuctionDto(null,
            new BigDecimal(100_000),
            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.MIDNIGHT),
            new ProductDto(product.getId(), product.getName(), product.getEnglishName(),
                product.getCategory().name(), product.getBrand().getName(),
                product.getBrand().getEnglishName()),
            size.getName(),
            type
        )
        def json = result.getResponse().getContentAsString(Charset.forName("UTF-8"))

        objectMapper.readValue(json, AuctionDto.class) == auctionDto

        where:
        url             || type
        "/auction/asks" || AuctionType.ASK
        "/auction/bids" || AuctionType.BID
    }

    def "return 400 if invalid update request - #url, #request.second"() {
        given:
        String targetUrl = url + "/1"
        AuctionPatchRequest patchRequest = (request as Tuple2<AuctionPatchRequest, String>).getV1()

        def builder = MockMvcRequestBuilders.patch(targetUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(patchRequest))

        when:
        def resultActions = mockMvc.perform(builder)

        then:
        resultActions.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))

        where:
        [request, url] << [
            [
                new Tuple2<>(new AuctionPatchRequest(new BigDecimal(-1), 1), "invalid price"),
                new Tuple2<>(new AuctionPatchRequest(new BigDecimal(10000), 0), "invalid under 1 due day"),
                new Tuple2<>(new AuctionPatchRequest(new BigDecimal(10000), 61), "exceed dueDays"),
                new Tuple2<>(new AuctionPatchRequest(new BigDecimal(10000), null), "null dueDays"),
                new Tuple2<>(new AuctionPatchRequest(null, 1), "null price"),
            ],
            ["/auction/asks", "/auction/bids"]
        ].combinations()
    }

    def "return 401 if there is no authentication when signing to the auction"() {
        given:
        String targetUrl = url + "/1/sign"

        def builder = post(targetUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        authenticationResolver.resolveArgument(_ as MethodParameter, _ as ModelAndViewContainer,
            _ as NativeWebRequest, _ as WebDataBinderFactory) >> null

        when:
        def resultActions = mockMvc.perform(builder)

        then:
        resultActions.andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))

        where:
        url << ["/auction/asks", "/auction/bids"]
    }

    def "return 200 when signing to the auction"() {
        given:
        String targetUrl = url + "/1/sign"

        def builder = post(targetUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        authenticationResolver.resolveArgument(_ as MethodParameter, _ as ModelAndViewContainer,
            _ as NativeWebRequest, _ as WebDataBinderFactory) >> Stub(User)

        auctionService.sign(_ as User, 1L) >> new SignAuctionResponse(1L, LocalDateTime.now())

        when:
        def resultActions = mockMvc.perform(builder)

        then:
        resultActions.andExpect(status().is(HttpStatus.OK.value()))

        where:
        url << ["/auction/asks", "/auction/bids"]
    }
}
