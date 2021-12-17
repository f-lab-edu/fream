package kr.flab.fream.mybatis.mapper.auction

import kr.flab.domain.auction.AuctionFixtures
import kr.flab.fream.DatabaseTest
import kr.flab.fream.domain.auction.model.AuctionType
import kr.flab.fream.domain.user.model.User
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
        def auction = AuctionFixtures.create("284000", product, product.getSize(1L), 60, type)

        auctionMapper.create(auction)

        expect:
        auctionMapper.cancel(auction) == 1

        and:
        def updatedAuction = auctionMapper.getAuction(auction.id)
        updatedAuction.canceledAt != null

        where:
        type << [AuctionType.ASK, AuctionType.BID]
    }

    def "update Bid"() {
        given:
        def product = getNikeDunkLowRetroBlack()
        def auction = AuctionFixtures.create("284000", product, product.getSize(1L), 60, AuctionType.BID)
        auctionMapper.create(auction)

        auction.update(new BigDecimal("280000"), 30L)

        expect:
        auctionMapper.update(auction) == 1
    }

    def "set bidder"() {
        given:
        def product = getNikeDunkLowRetroBlack()
        def auction = AuctionFixtures.create("284000", product, product.getSize(1L), 60, AuctionType.BID)

        auctionMapper.create(auction)

        def user = new User()
        user.id = 1L

        auction.sign(user)

        expect:
        auctionMapper.update(auction) == 1
        def resultAuction = auctionMapper.getAuction(auction.getId())
        def bidder = resultAuction.getBidder()
        bidder != null
        bidder.getId() == 1L
    }

    def "get auction using x lock"() {
        given:
        def product = getNikeDunkLowRetroBlack()
        def auction = AuctionFixtures.create("284000", product, product.getSize(1L), 60, AuctionType.BID)

        auctionMapper.create(auction)

        when:
        def auctionWithXLock = auctionMapper.getAuctionForUpdate(auction.getId())

        then:
        auctionWithXLock.getId() == auction.getId()
    }

}
