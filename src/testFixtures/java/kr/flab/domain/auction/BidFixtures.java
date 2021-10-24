package kr.flab.domain.auction;

import static kr.flab.domain.product.SizeFixtures.getSizeFromList;
import static kr.flab.domain.product.SizeFixtures.getSneakersSizes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import kr.flab.fream.domain.auction.model.Bid;
import kr.flab.fream.domain.product.model.Product;

public class BidFixtures {

    private BidFixtures() {
    }

    public static Bid createBid(String price, Product product, String size, int endOfDays) {
        Bid bid = new Bid();
        bid.setPrice(new BigDecimal(price));
        bid.setProduct(product);
        bid.setSize(getSizeFromList(getSneakersSizes().getSizeList(), size));
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime dueDate =
                LocalDateTime.of(LocalDate.now().plusDays(endOfDays + 1L), LocalTime.MIDNIGHT);
        bid.setCreatedAt(createdAt);
        bid.setDueDate(dueDate);

        return bid;
    }

}
