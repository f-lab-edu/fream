package kr.flab.domain.auction;

import java.math.BigDecimal;
import kr.flab.fream.controller.auction.AuctionRequest;
import kr.flab.fream.domain.auction.model.Auction;
import kr.flab.fream.domain.auction.model.AuctionType;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.product.model.Size;
import kr.flab.fream.domain.user.model.User;
import kr.flab.user.UserFixtures;

public class AuctionFixtures {

    private AuctionFixtures() {
    }

    public static Auction create(String price, Product product, Size size, long endOfDays,
            AuctionType type) {
        User user = UserFixtures.tester();

        AuctionRequest request = new AuctionRequest(new BigDecimal(price), product.getId(),
                size.getId(), user.getId(), endOfDays, type);
        Auction auction = type.constructor.apply(request);
        auction.setUser(user);
        auction.setProduct(product);
        auction.setSize(product.getSize(1L));

        return auction;
    }

}
