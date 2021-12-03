package kr.flab.fream.domain.auction.model;

class Inactive implements AuctionState {

    @Override
    public void cancel(Auction auction) {
        auction.setState(new Canceled());
    }

    @Override
    public void update(Auction auction) {
        auction.setState(new Active());
    }

    @Override
    public void finish(Auction auction) {
        throw new IllegalStateException("이미 만료된 입찰입니다.");
    }

}
