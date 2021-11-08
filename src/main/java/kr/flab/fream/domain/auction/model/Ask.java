package kr.flab.fream.domain.auction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import kr.flab.fream.controller.auction.AuctionRequest;

/**
 * 판매 입찰을 표현한 모델.
 *
 * @since 1.0.0
 */
public class Ask extends Auction {

    public Ask(Long id, BigDecimal price, LocalDateTime createdAt, LocalDateTime dueDate,
            LocalDateTime canceledAt, LocalDateTime signedAt,
            AuctionType type) {
        super(id, price, createdAt, dueDate, canceledAt, signedAt, type);
    }

    private Ask(AuctionRequest auctionRequest, AuctionType type) {
        super(auctionRequest, type);
    }

    public static Ask of(AuctionRequest request) {
        return new Ask(request, AuctionType.ASK);
    }

}
