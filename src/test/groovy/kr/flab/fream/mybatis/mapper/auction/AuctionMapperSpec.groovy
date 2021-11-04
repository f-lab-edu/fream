package kr.flab.fream.mybatis.mapper.auction

import kr.flab.domain.auction.AuctionFixtures
import kr.flab.fream.domain.auction.model.AuctionType
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

import static kr.flab.domain.product.ProductFixtures.nikeDunkLowRetroBlack

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuctionMapperSpec extends Specification {

    @Autowired
    AuctionMapper auctionMapper

    def "save Ask and Bid"() {
        given:
        def auction = AuctionFixtures.create("284000", getNikeDunkLowRetroBlack(), "265", 60, type)

        expect:
        auctionMapper.create(auction) == 1

        where:
        type << [AuctionType.ASK, AuctionType.BID]
    }

    def "cancel auction by removing that"() {
        given:
        def auction = AuctionFixtures.create("284000", getNikeDunkLowRetroBlack(), "265", 60, AuctionType.ASK)
        auctionMapper.create(auction)

        expect:
        auctionMapper.cancel(auction) == 1

        and:
        def updatedAuction = auctionMapper.getAuction(auction.id)
        updatedAuction.canceledAt != null
    }

    def "update Bid"() {
        given:
        def auction = AuctionFixtures.create("284000", getNikeDunkLowRetroBlack(), "265", 60, AuctionType.ASK)
        auctionMapper.create(auction)

        def newDueDate = LocalDateTime.of(LocalDate.now().plusDays(31), LocalTime.MIDNIGHT)
        def newPrice = new BigDecimal("280000")
        auction.setDueDate(newDueDate)
        auction.setPrice(newPrice)

        expect:
        auctionMapper.update(auction) == 1
    }

}
