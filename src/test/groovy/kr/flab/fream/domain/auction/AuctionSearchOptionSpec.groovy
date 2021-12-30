package kr.flab.fream.domain.auction

import kr.flab.fream.domain.auction.model.AuctionType
import spock.lang.Specification

class AuctionSearchOptionSpec extends Specification {

    def 'throw IllegalArgumentException if only one of "last Price" and "lastActionId" is set'() {
        given:
        def builder = AuctionSearchOption.builder()
            .lastPrice(lastPrice)
            .lastAuctionId(lastAuctionId)

        when:
        builder.build()

        then:
        thrown(IllegalArgumentException)

        where:
        lastPrice                || lastAuctionId
        new BigDecimal("500000") || null
        null                     || 1L
    }

    def 'accept if both "last Price" and "lastActionId" is set or not'() {
        given:
        def builder = AuctionSearchOption.builder()
            .auctionType(AuctionType.ASK)
            .productId(1L)
            .lastPrice(lastPrice)
            .lastAuctionId(lastAuctionId)

        when:
        builder.build()

        then:
        noExceptionThrown()

        where:
        lastPrice                || lastAuctionId
        null                     || null
        new BigDecimal("500000") || 1L
    }

}
