package kr.flab.fream.domain.auction.service

import kr.flab.domain.product.ProductFixtures
import kr.flab.fream.controller.auction.AuctionRequest
import kr.flab.fream.domain.auction.model.AuctionType
import kr.flab.fream.domain.product.model.Product
import kr.flab.fream.domain.product.service.ProductService
import kr.flab.fream.domain.user.model.User
import kr.flab.fream.domain.user.service.UserService
import kr.flab.fream.mybatis.mapper.auction.AuctionMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = [AuctionService.class])
class AuctionServiceSpec extends Specification {

    Product targetProduct = ProductFixtures.getNikeDunkLowRetroBlack()

    @SpringBean
    AuctionMapper auctionMapper = Mock()

    @SpringBean
    UserService userService = Stub() {
        getUser(_ as Long) >> { Long id -> new User(id) }
    }

    @SpringBean
    ProductService productService = Stub() {
        getProduct(_ as Long) >> targetProduct
    }

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
        auction.getProduct() == targetProduct
        auction.getSize() == targetProduct.getSize(1L)

        where:
        type << [AuctionType.ASK, AuctionType.BID]
    }

}
