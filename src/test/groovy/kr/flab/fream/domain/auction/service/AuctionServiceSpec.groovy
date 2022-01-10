package kr.flab.fream.domain.auction.service

import kr.flab.domain.auction.AuctionFixtures
import kr.flab.domain.product.ProductFixtures
import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.controller.auction.AuctionPatchRequest
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
import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime
import java.util.stream.Collectors

import static org.assertj.core.api.Assertions.assertThat

class AuctionServiceSpec extends Specification {

    private static Product targetProduct = ProductFixtures.getNikeDunkLowRetroBlack()

    AuctionMapper auctionMapper

    UserMapper userMapper

    ProductService productService

    ModelMapper modelMapper = new ModelMapperConfiguration().modelMapper()

    AuctionService sut

    void setup() {
        productService = Stub()
        productService.getProduct(_ as Long) >> targetProduct

        userMapper = Stub()
        userMapper.getUser(_ as Long) >> { Long id -> new User(id) }

        auctionMapper = Stub()

        sut = new AuctionService(auctionMapper, userMapper, productService, modelMapper)
    }

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
        auction.price == new BigDecimal("100000")
        auction.product.id == 1L
        auction.product.name == targetProduct.name
        auction.sizeName == targetProduct.sizes.getSize(1L).name
        assertThat(Duration.between(LocalDateTime.now(), auction.dueDate).toDays())
            .isBetween(59L, 60L)

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

    def "update an auction"() {
        given:
        auctionMapper.getAuction(_ as Long) >> auction()
        def originDate = auction().dueDate
        def request = new AuctionPatchRequest(new BigDecimal("150000"), 30);

        when:
        def result = sut.update(1L, request)

        then:
        result.getPrice() == request.price
        Duration.between(result.dueDate, originDate).toDays() <= 30L
    }

    def "cancel an auction"() {
        given:
        def targetAuction = auction()
        auctionMapper.getAuction(targetProduct.id as Long) >> targetAuction

        when:
        sut.cancel(targetProduct.id)

        then:
        noExceptionThrown()
    }

    private static def auction() {
        def auction = new Ask(1L, new BigDecimal(100_000), null, LocalDateTime.now().plusDays(60), null, null, AuctionType.ASK)

        auction.user = UserFixtures.firstUser()
        auction.product = targetProduct

        return auction
    }

}
