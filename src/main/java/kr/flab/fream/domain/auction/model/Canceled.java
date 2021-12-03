package kr.flab.fream.domain.auction.model;

class Canceled implements AuctionState {

    private static final String REJECT_MESSAGE = "이미 취소된 입찰입니다.";

    @Override
    public void cancel(Auction auction) {
        throw new IllegalStateException(REJECT_MESSAGE);
    }

    @Override
    public void update(Auction auction) {
        throw new IllegalStateException(REJECT_MESSAGE);
    }

    @Override
    public void finish(Auction auction) {
        throw new IllegalStateException(REJECT_MESSAGE);
    }

}
