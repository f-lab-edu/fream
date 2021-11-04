package kr.flab.domain.auction;

import static kr.flab.domain.product.SizeFixtures.getSizeFromList;
import static kr.flab.domain.product.SizeFixtures.getSneakersSizes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import kr.flab.fream.domain.auction.model.Auction;
import kr.flab.fream.domain.auction.model.AuctionType;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.user.model.User;

public class AuctionFixtures {

    private AuctionFixtures() {
    }

    public static Auction create(String price, Product product, String size, int endOfDays,
            AuctionType type) {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime dueDate =
                LocalDateTime.of(LocalDate.now().plusDays(endOfDays + 1L), LocalTime.MIDNIGHT);

        User user = new User(1L);

        return new Auction(
                null, new BigDecimal(price), createdAt, dueDate, product,
                getSizeFromList(getSneakersSizes().getSizeList(), size), user, type, null, null
        );
    }

}
