package kr.flab.fream.domain.auction.model;

import kr.flab.fream.controller.auction.AuctionRequest;

/**
 * 판매 입찰을 표현한 모델.
 *
 * @since 1.0.0
 */
public final class Ask extends Auction {

    private Ask(AuctionRequest auctionRequest, AuctionType type) {
        super(auctionRequest, type);
    }

    public static Ask of(AuctionRequest request) {
        return new Ask(request, AuctionType.ASK);
    }

}
