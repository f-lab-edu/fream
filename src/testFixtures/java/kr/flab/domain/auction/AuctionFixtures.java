package kr.flab.domain.auction;

import java.math.BigDecimal;
import kr.flab.fream.controller.auction.AuctionRequest;
import kr.flab.fream.domain.auction.model.Ask;
import kr.flab.fream.domain.auction.model.Auction;
import kr.flab.fream.domain.auction.model.AuctionType;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.product.model.Size;
import kr.flab.fream.domain.user.model.User;

public class AuctionFixtures {

    private AuctionFixtures() {
    }

    public static Auction create(String price, Product product, Size size, long endOfDays,
            AuctionType type) {
        User user = new User(1L);

        AuctionRequest request = new AuctionRequest(new BigDecimal(price), product.getId(),
                size.getId(), 1L, endOfDays, type);
        Auction ask = Ask.of(request);
        ask.setUser(user);
        ask.setProduct(product);
        ask.setSize(product.getSize(1L));

        return ask;
    }

}
