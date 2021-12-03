package kr.flab.fream.domain.auction.model


import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AuctionTest extends Specification {

    def static nextDay() {
        return LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT)
    }

    def static activeAuction() {
        def auction = new Ask(1L, new BigDecimal("100000"), LocalDateTime.now(), nextDay(), null, null, AuctionType.ASK)
        assert auction.state.getClass() == Active

        return auction
    }

    def static inactiveAuction() {
        def baseDate = LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.MIDNIGHT)
        def auction = new Ask(1L, new BigDecimal("100000"), baseDate, baseDate, null, null, AuctionType.ASK)
        assert auction.state.getClass() == Inactive

        return auction
    }

    def static finishedAuction() {
        def auction = new Ask(1L, new BigDecimal("100000"), LocalDateTime.now(), nextDay(), null, LocalDateTime.now(), AuctionType.ASK)
        assert auction.state.getClass() == Finished

        return auction
    }

    def static canceledAuction() {
        def auction = new Ask(1L, new BigDecimal("100000"), LocalDateTime.now(), nextDay(), LocalDateTime.now(), null, AuctionType.ASK)
        assert auction.state.getClass() == Canceled

        return auction
    }

    def "update the active auction"() {
        given:
        def auction = activeAuction()
        def newPrice = 200_000L
        def newDueDays = 30L

        when:
        auction.update(new BigDecimal(newPrice), newDueDays)

        then:
        auction.getPrice() == new BigDecimal(newPrice)
        auction.getDueDate() == LocalDateTime.of(LocalDate.now().plusDays(30 + 1L), LocalTime.MIDNIGHT)
        auction.state.getClass() == Active
    }

    def "sign the active auction"() {
        given:
        def auction = activeAuction()

        expect:
        auction.sign()
        auction.state.getClass() == Finished
    }

    def "cancel the active auction"() {
        given:
        def auction = activeAuction()

        expect:
        auction.cancel()
        auction.state.getClass() == Canceled
    }

    def "update the inactive auction and change a state to Active"() {
        given:
        def auction = inactiveAuction()
        def newPrice = 200_000L
        def newDueDays = 30L

        when:
        auction.update(new BigDecimal(newPrice), newDueDays)

        then:
        auction.getPrice() == new BigDecimal(newPrice)
        auction.getDueDate() == LocalDateTime.of(LocalDate.now().plusDays(30 + 1L), LocalTime.MIDNIGHT)
        auction.state.getClass() == Active
    }

    def "cancel the inactive auction"() {
        given:
        def auction = inactiveAuction()

        expect:
        auction.cancel()
        auction.state.getClass() == Canceled
    }

    def "cannot sign an inactive auction"() {
        given:
        def auction = inactiveAuction()

        when:
        auction.sign()

        then:
        thrown(IllegalStateException)
    }

    def "cannot update a finished auction"() {
        given:
        def auction = finishedAuction()

        when:
        auction.update(new BigDecimal(0), 30)

        then:
        thrown(IllegalStateException)
    }

    def "cannot sign the finished auction"() {
        given:
        def auction = finishedAuction()

        when:
        auction.sign()

        then:
        thrown(IllegalStateException)
    }

    def "cannot cancel the finished auction"() {
        given:
        def auction = finishedAuction()

        when:
        auction.cancel()

        then:
        thrown(IllegalStateException)
    }

    def "cannot update a canceled auction"() {
        given:
        def auction = canceledAuction()

        when:
        auction.update(new BigDecimal(0), 30)

        then:
        thrown(IllegalStateException)
    }

    def "cannot sign a canceled auction"() {
        given:
        def auction = canceledAuction()

        when:
        auction.sign()

        then:
        thrown(IllegalStateException)
    }

    def "cannot cancel a canceled auction"() {
        given:
        def auction = canceledAuction()

        when:
        auction.cancel()

        then:
        thrown(IllegalStateException)
    }

}
