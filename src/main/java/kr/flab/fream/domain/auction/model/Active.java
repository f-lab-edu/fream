package kr.flab.fream.domain.auction.model;

class Active implements AuctionState {

    @Override
    public void cancel(Auction auction) {
        auction.setState(new Canceled());
    }

    @Override
    public void update(Auction auction) {
        auction.setState(this);
    }

    @Override
    public void finish(Auction auction) {
        auction.setState(new Finished());
    }
}
