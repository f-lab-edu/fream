package kr.flab.fream.domain.auction.service


import kr.flab.fream.IntegrationSpec
import kr.flab.fream.controller.auction.AuctionRequest
import kr.flab.fream.controller.auction.AuctionSummaryByPriceAndSizeWithQuantity
import kr.flab.fream.domain.auction.model.AuctionType
import kr.flab.fream.mybatis.mapper.auction.AuctionMapper
import kr.flab.fream.mybatis.mapper.user.UserMapper
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql

import static org.assertj.core.api.Assertions.assertThat

class AuctionServiceIntegrationSpec extends IntegrationSpec {

    @Autowired
    AuctionMapper auctionMapper

    @Autowired
    ModelMapper modelMapper

    @Autowired
    AuctionService sut

    @Autowired
    UserMapper userMapper

    @Sql("/db-test-data/auction/get-ask-summaries.sql")
    def "get ASK summaries"() {
        given:
        def type = AuctionType.ASK
        def productId = 2L

        expect:
        def summaries = sut.getAuctionSummaries(type, productId, null, null)
        assertThat(summaries)
            .usingRecursiveComparison()
            .isEqualTo(Arrays.asList(
                new AuctionSummaryByPriceAndSizeWithQuantity("275", new BigDecimal("550000.00"), 2, AuctionType.ASK),
                new AuctionSummaryByPriceAndSizeWithQuantity("275", new BigDecimal("575000.00"), 1, AuctionType.ASK),
                new AuctionSummaryByPriceAndSizeWithQuantity("275", new BigDecimal("576000.00"), 1, AuctionType.ASK),
                new AuctionSummaryByPriceAndSizeWithQuantity("275", new BigDecimal("577000.00"), 1, AuctionType.ASK),
                new AuctionSummaryByPriceAndSizeWithQuantity("275", new BigDecimal("578000.00"), 1, AuctionType.ASK),
                new AuctionSummaryByPriceAndSizeWithQuantity("275", new BigDecimal("579000.00"), 1, AuctionType.ASK),
                new AuctionSummaryByPriceAndSizeWithQuantity("275", new BigDecimal("583000.00"), 1, AuctionType.ASK),
                new AuctionSummaryByPriceAndSizeWithQuantity("280", new BigDecimal("583000.00"), 2, AuctionType.ASK),
                new AuctionSummaryByPriceAndSizeWithQuantity("280", new BigDecimal("584000.00"), 1, AuctionType.ASK),
                new AuctionSummaryByPriceAndSizeWithQuantity("280", new BigDecimal("588000.00"), 1, AuctionType.ASK),
            ))
    }

    @Sql("/db-test-data/auction/get-bid-summaries.sql")
    def "get BID summaries"() {
        given:
        def type = AuctionType.BID
        def productId = 2L
        def sizeId = 10L

        when:
        def summaries =
            sut.getAuctionSummaries(type, productId, sizeId, lastPrice)

        then:
        assertThat(summaries)
            .usingRecursiveComparison()
            .isEqualTo(expect)

        where:
        lastPrice << [null, new BigDecimal("507000")]
        expect << [
            Arrays.asList(
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("530000.00"), 3, AuctionType.BID),
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("529000.00"), 1, AuctionType.BID),
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("528000.00"), 1, AuctionType.BID),
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("525000.00"), 2, AuctionType.BID),
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("524000.00"), 1, AuctionType.BID),
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("522000.00"), 1, AuctionType.BID),
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("518000.00"), 1, AuctionType.BID),
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("515000.00"), 1, AuctionType.BID),
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("512000.00"), 1, AuctionType.BID),
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("507000.00"), 1, AuctionType.BID),
            ),
            Arrays.asList(
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("500000.00"), 6, AuctionType.BID),
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("499000.00"), 1, AuctionType.BID),
                new AuctionSummaryByPriceAndSizeWithQuantity("265", new BigDecimal("495000.00"), 1, AuctionType.BID),
            ),
        ]
    }

    def "create an auction"() {
        given:
        def request = new AuctionRequest(
            new BigDecimal("100000"),
            1,
            1,
            1,
            30,
             AuctionType.ASK
        )

        when:
        def response = sut.createAuction(request)

        then:
        response != null
    }

    def "sign an auction"() {
        given:
        def user = userMapper.getUserById(2)
        def auctionId = 1L

        when:
        def response = sut.sign(user, auctionId)

        then:
        response != null
    }

}
