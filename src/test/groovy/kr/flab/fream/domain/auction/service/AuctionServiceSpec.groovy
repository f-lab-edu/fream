package kr.flab.fream.domain.auction.service

import kr.flab.domain.auction.AuctionFixtures
import kr.flab.domain.product.ProductFixtures
import kr.flab.fream.controller.auction.AuctionDto
import kr.flab.fream.controller.auction.AuctionRequest
import kr.flab.fream.domain.auction.AuctionSearchOption
import kr.flab.fream.domain.auction.dto.SignAuctionResponse
import kr.flab.fream.domain.auction.model.Ask
import kr.flab.fream.domain.auction.model.AuctionType
import kr.flab.fream.domain.product.model.Product
import kr.flab.fream.domain.product.service.ProductService
import kr.flab.fream.domain.user.model.User
import kr.flab.fream.mybatis.mapper.auction.AuctionMapper
import kr.flab.fream.mybatis.mapper.user.UserMapper
import kr.flab.user.UserFixtures
import org.modelmapper.ModelMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime
import java.util.stream.Collectors

import static org.assertj.core.api.Assertions.assertThat

@SpringBootTest(classes = [AuctionService, ModelMapper])
class AuctionServiceSpec extends Specification {

    private static Product targetProduct = ProductFixtures.getNikeDunkLowRetroBlack()

    @SpringBean
    AuctionMapper auctionMapper = Stub() {
    }

    @SpringBean
    UserMapper userMapper = Stub() {
        getUser(_ as Long) >> { Long id -> new User(id) }
    }

    @SpringBean
    ProductService productService = Stub() {
        getProduct(_ as Long) >> targetProduct
    }

    @Autowired
    ModelMapper modelMapper

    @Autowired
    AuctionService sut

    def "throw IllegalArgumentException if product does not have a given size"() {
        given:
        def requestWithWrongSizeId =
            new AuctionRequest(new BigDecimal(100_000), 1L, 999_999L, 1L, 60, AuctionType.ASK)

        when:
        sut.createAuction(requestWithWrongSizeId)

        then:
        thrown(IllegalArgumentException)
    }

    def "create an auction"() {
        given:
        def request = new AuctionRequest(new BigDecimal(100_000), 1L, 1L, 1L, 60, type)

        when:
        def auction = sut.createAuction(request)

        then:
        auction == modelMapper.map(targetProduct, AuctionDto.getTypeObject())

        where:
        type << [AuctionType.ASK, AuctionType.BID]
    }

    def "sign an auction"() {
        given:
        def user = UserFixtures.tester()
        def targetAuction = auction()
        def auctionId = targetAuction.getId()

        auctionMapper.getAuctionForUpdate(auctionId) >> targetAuction

        when:
        def response = sut.sign(user, auctionId)

        then:
        response.getId() == auctionId
        response.getSignedAt() != null
        response instanceof SignAuctionResponse
    }

    def "get auctions"() {
        given:
        def searchOption = Mock(AuctionSearchOption)
        def product = ProductFixtures.getNikeDunkLowRetroBlack()
        auctionMapper.getAuctions(searchOption) >> [
            AuctionFixtures.create("284000", product, product.getSize(1L), 60, AuctionType.ASK),
            AuctionFixtures.create("287000", product, product.getSize(1L), 60, AuctionType.ASK),
            AuctionFixtures.create("289000", product, product.getSize(1L), 60, AuctionType.ASK),
        ]

        when:
        def auctions = sut.getAuctions(searchOption)

        then:
        assertThat(auctions.stream().map(a -> a.getPrice()).collect(Collectors.toList()))
            .usingRecursiveComparison()
            .isEqualTo([
                new BigDecimal("284000"),
                new BigDecimal("287000"),
                new BigDecimal("289000"),
            ])
    }

    private static def auction() {
        def auction = new Ask(1L, new BigDecimal(100_000), null, LocalDateTime.now().plusDays(60), null, null, AuctionType.ASK)

        auction.user = UserFixtures.firstUser()
        auction.product = targetProduct

        return auction
    }

}
