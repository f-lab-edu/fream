package kr.flab.fream.mybatis.mapper.auction

import kr.flab.domain.auction.AuctionFixtures
import kr.flab.fream.DatabaseTest
import kr.flab.fream.domain.auction.model.AuctionType
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

import static kr.flab.domain.product.ProductFixtures.nikeDunkLowRetroBlack

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuctionMapperSpec extends DatabaseTest {

    @Autowired
    AuctionMapper auctionMapper

    def "save Auction"() {
        given:
        def product = getNikeDunkLowRetroBlack()
        def auction = AuctionFixtures.create("284000", product, product.getSize(1L), 60, AuctionType.ASK)

        expect:
        auctionMapper.create(auction) == 1
    }

    def "cancel auction by removing that"() {
        given:
        def product = getNikeDunkLowRetroBlack()
        def auction = AuctionFixtures.create("284000", product, product.getSize(1L), 60, AuctionType.ASK)

        auctionMapper.create(auction)

        expect:
        auctionMapper.cancel(auction) == 1

        and:
        def updatedAuction = auctionMapper.getAuction(auction.id)
        updatedAuction.canceledAt != null
    }

    def "update Bid"() {
        given:
        def product = getNikeDunkLowRetroBlack()
        def auction = AuctionFixtures.create("284000", product, product.getSize(1L), 60, AuctionType.BID)
        auctionMapper.create(auction)

        auction.setDueDaysFromToday(30)
        auction.setPrice(new BigDecimal("280000"))

        expect:
        auctionMapper.update(auction) == 1
    }

}
