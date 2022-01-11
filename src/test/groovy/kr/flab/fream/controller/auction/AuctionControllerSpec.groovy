package kr.flab.fream.controller.auction

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import kr.flab.domain.product.ProductFixtures
import kr.flab.fream.auth.AbstractAuthenticationResolver
import kr.flab.fream.config.FormattingConfiguration
import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.config.WebConfig
import kr.flab.fream.controller.product.ProductDto
import kr.flab.fream.domain.auction.AuctionSearchOption
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
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import spock.lang.Specification

import java.nio.charset.Charset
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
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

    @SpringBean(name = "authenticationResolver")
    AbstractAuthenticationResolver authenticationResolver = Stub()

    @SuppressWarnings("unused")
    // IntelliJ 가 spock setup 을 인식하지 못함
    void setup() {
        objectMapper.registerModule(new JavaTimeModule())
    }

    def "return 400 invalid AuctionRequest '#testcase'"() {
        given:
        def session = new MockHttpSession();
        session.setAttribute("userInfo","test")
        def resultActions = mockMvc.perform(post(ASK_URL).session(session).contentType(MediaType.APPLICATION_JSON)
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
        def session = new MockHttpSession();
        session.setAttribute("userInfo","test")
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
            .session(session)
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
        def session = new MockHttpSession();
        session.setAttribute("userInfo","test")
        String targetUrl = url + "/1"
        AuctionPatchRequest patchRequest = (request as Tuple2<AuctionPatchRequest, String>).getV1()

        def builder = MockMvcRequestBuilders.patch(targetUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(patchRequest))

        when:
        def resultActions = mockMvc.perform(builder.session(session))

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
        def session = new MockHttpSession();
        session.setAttribute("userInfo","test")
        def builder = post(targetUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        authenticationResolver.resolveArgument(_ as MethodParameter, _ as ModelAndViewContainer,
            _ as NativeWebRequest, _ as WebDataBinderFactory) >> null

        when:
        def resultActions = mockMvc.perform(builder.session(session))

        then:
        resultActions.andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))

        where:
        url << ["/auction/asks", "/auction/bids"]
    }

    def "return 200 when signing to the auction"() {
        given:
        String targetUrl = url + "/1/sign"
        def session = new MockHttpSession();
        session.setAttribute("userInfo","test")
        def builder = post(targetUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        authenticationResolver.resolveArgument(_ as MethodParameter, _ as ModelAndViewContainer,
            _ as NativeWebRequest, _ as WebDataBinderFactory) >> Stub(User)

        auctionService.sign(_ as User, 1L) >> new SignAuctionResponse(1L, LocalDateTime.now())

        when:
        def resultActions = mockMvc.perform(builder.session(session))

        then:
        resultActions.andExpect(status().is(HttpStatus.OK.value()))

        where:
        url << ["/auction/asks", "/auction/bids"]
    }

    def "return 400 if the product id not specified"() {
        given:
        String targetUrl = url + "/summaries"
        def builder = get(targetUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        when:
        def resultActions = mockMvc.perform(builder)

        then:
        resultActions.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))

        where:
        url << ["/auction/asks", "/auction/bids"]
    }

    def "return 400 if inputs for the search option is invalid"() {
        given:
        def params = new LinkedMultiValueMap<String, String>()

        for (def entry : (paramMap as Map<String, String>).entrySet()) {
            params.add(entry.getKey(), entry.getValue())
        }

        def builder = get(url as String)
            .params(params)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        when:
        def resultActions = mockMvc.perform(builder)

        then:
        resultActions.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))

        where:
        [url, paramMap] << [
            ["/auction/asks", "/auction/bids"],
            [
                Map.of(), // no product Id
                Map.of("productId", "1", "lastPrice", "500000"), // lastPrice exists but lastAuctionId is null
                Map.of("productId", "2", "lastAuctionId", "1"), // lastAuctionId exists but lastPrice is null
                Map.of("productId", "2", "items", "0"), // items value is less than 1
            ]
        ].combinations()
    }

    def "return 200 if inputs for the search option is valid"() {
        given:
        def params = new LinkedMultiValueMap<String, String>()

        for (def entry : (paramMap as Map<String, String>).entrySet()) {
            params.add(entry.getKey(), entry.getValue())
        }

        def builder = get(url as String)
            .params(params)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        auctionService.getAuctions(_ as AuctionSearchOption) >> Collections.emptyList()

        when:
        def resultActions = mockMvc.perform(builder)

        then:
        resultActions.andExpect(status().is(HttpStatus.OK.value()))

        where:
        [url, paramMap] << [
            ["/auction/asks", "/auction/bids"],
            [
                Map.of("productId", "1"),
                Map.of("productId", "1", "lastPrice", "500000", "lastAuctionId", "2"),
                Map.of("productId", "1", "sizeId", "2", "items", "1")
            ]
        ].combinations()
    }

    def "return 204 when canceling an auction"() {
        given:
        def session = new MockHttpSession();
        session.setAttribute("userInfo","test")

        def builder = delete(url as String + "/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        when:
        def resultActions = mockMvc.perform(builder.session(session))

        then:
        resultActions.andExpect(status().is(HttpStatus.NO_CONTENT.value()))

        where:
        url << ["/auction/asks", "/auction/bids"]
    }

    def "return 400 if input values wrong when requesting modify an auction"() {
        given:
        def session = new MockHttpSession();
        session.setAttribute("userInfo","test")

        def id = 1L

        def builder = patch(url as String + "/" + id)
            .content(objectMapper.writeValueAsString(requestBody))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        when:
        def resultActions = mockMvc.perform(builder.session(session))

        then:
        resultActions.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))

        where:
        [url, requestBody] << [
            ["/auction/asks", "/auction/bids"],
            [
                new AuctionPatchRequest(null, 1),
                new AuctionPatchRequest(new BigDecimal("100000"), null),
                new AuctionPatchRequest(new BigDecimal("-1"), 1),
                new AuctionPatchRequest(new BigDecimal("100000"), 0),
                new AuctionPatchRequest(new BigDecimal("100000"), 61)
            ]
        ].combinations()
    }

    def "return 200 when requesting modify an auction with valid inputs"() {
        given:
        def session = new MockHttpSession();
        session.setAttribute("userInfo","test")

        def id = 1L

        def builder = patch(url as String + "/" + id)
            .content(objectMapper.writeValueAsString(requestBody))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        auctionService.update(_ as Long, _ as AuctionPatchRequest) >> null

        when:
        def resultActions = mockMvc.perform(builder.session(session))

        then:
        resultActions.andExpect(status().is(HttpStatus.OK.value()))

        where:
        [url, requestBody] << [
            ["/auction/asks", "/auction/bids"],
            [
                new AuctionPatchRequest(new BigDecimal("100000"), 1),
            ]
        ].combinations()
    }

    def "return 400 if product ID is null when getting auction summaries"() {
        given:
        def builder = get(url as String)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        auctionService.getAuctionSummaries(_ as AuctionType, _ as Long, _ as Long, _ as BigDecimal)
            >> new ArrayList<AuctionSummaryByPriceAndSizeWithQuantity>()

        when:
        def resultActions = mockMvc.perform(builder)

        then:
        resultActions.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))

        where:
        url << ["/auction/asks/summaries", "/auction/bids/summaries"]
    }

    def "return 200 if get auction summaries"() {
        given:
        def builder = get(url as String)
            .param("productId", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        auctionService.getAuctionSummaries(_ as AuctionType, _ as Long, _ as Long, _ as BigDecimal)
            >> new ArrayList<AuctionSummaryByPriceAndSizeWithQuantity>()

        when:
        def resultActions = mockMvc.perform(builder)

        then:
        resultActions.andExpect(status().is(HttpStatus.OK.value()))

        where:
        url << ["/auction/asks/summaries", "/auction/bids/summaries"]
    }
    
}

