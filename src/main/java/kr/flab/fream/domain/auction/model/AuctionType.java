package kr.flab.fream.domain.auction.model;

import java.util.function.Function;
import kr.flab.fream.controller.auction.AuctionRequest;
import lombok.RequiredArgsConstructor;

/**
 * 입찰 종류를 나타내는 enum.
 *
 * @since 1.0.0
 */
@RequiredArgsConstructor
public enum AuctionType {

    /**
     * 판매 입찰.
     */
    ASK(Ask::of),
    /**
     * 구매 입찰.
     */
    BID(Bid::of);

    public final Function<AuctionRequest, Auction> constructor;

}
