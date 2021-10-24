package kr.flab.fream.mybatis.mapper.auction

import kr.flab.domain.auction.BidFixtures
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
class BidMapperSpec extends Specification {

    @Autowired
    BidMapper bidMapper

    def "save Bid"() {
        given:
        def bid = BidFixtures.createBid("284000", getNikeDunkLowRetroBlack(), "265", 60)

        expect:
        bidMapper.create(bid) == 1
    }

    def "cancel Bid by removing that"() {
        given:
        def bid = BidFixtures.createBid("284000", getNikeDunkLowRetroBlack(), "265", 60)
        bidMapper.create(bid)

        expect:
        bidMapper.cancel(bid) == 1
    }

    def "update Bid"() {
        given:
        def bid = BidFixtures.createBid("284000", getNikeDunkLowRetroBlack(), "265", 60)
        bidMapper.create(bid)

        def newDueDate = LocalDateTime.of(LocalDate.now().plusDays(31), LocalTime.MIDNIGHT)
        def newPrice = new BigDecimal("280000")
        bid.setDueDate(newDueDate)
        bid.setPrice(newPrice)

        expect:
        bidMapper.update(bid) == 1
    }

}
