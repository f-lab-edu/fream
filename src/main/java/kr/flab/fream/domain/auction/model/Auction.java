package kr.flab.fream.domain.auction.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import kr.flab.fream.controller.auction.AuctionRequest;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.product.model.Size;
import kr.flab.fream.domain.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 판매 입찰, 구매 입찰의 공통 부분을 지칭하는 모델.
 *
 * @since 1.0.0
 */
@NoArgsConstructor
//@AllArgsConstructor
@Getter
public class Auction {

    private Long id;
    @Setter
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
    private LocalDateTime canceledAt;
    private LocalDateTime signedAt;
    @Setter
    private AuctionType type;

    @Setter
    private Product product;
    @Setter
    private Size size;
    @Setter
    private User user;

    protected Auction(Long id, BigDecimal price, LocalDateTime createdAt, LocalDateTime dueDate,
            LocalDateTime canceledAt, LocalDateTime signedAt, AuctionType type) {
        this.id = id;
        this.price = price;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.canceledAt = canceledAt;
        this.signedAt = signedAt;
        this.type = type;
    }

    protected Auction(AuctionRequest auctionRequest, AuctionType type) {
        this.price = auctionRequest.getPrice();
        this.type = type;
        setDueDate(auctionRequest.getDueDays());
    }

    private void setDueDate(long dueDays) {
        this.dueDate = LocalDateTime.of(LocalDate.now().plusDays(dueDays + 1L),
                LocalTime.MIDNIGHT);
    }

    public void setDueDaysFromToday(long dueDays) {
        setDueDate(dueDays);
    }

}
