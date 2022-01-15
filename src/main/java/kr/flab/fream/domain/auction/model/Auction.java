package kr.flab.fream.domain.auction.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.function.Predicate;
import kr.flab.fream.controller.auction.AuctionRequest;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.product.model.Size;
import kr.flab.fream.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 판매 입찰, 구매 입찰의 공통 부분을 지칭하는 모델.
 *
 * @since 1.0.0
 */
@NoArgsConstructor
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

    private User bidder;

    @Setter
    private AuctionState state;

    protected Auction(Long id, BigDecimal price, LocalDateTime createdAt, LocalDateTime dueDate,
            LocalDateTime canceledAt, LocalDateTime signedAt, AuctionType type) {
        this.id = id;
        this.price = price;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.canceledAt = canceledAt;
        this.signedAt = signedAt;
        this.type = type;
        this.state = AuctionStateFactory.createState(this);
    }

    protected Auction(AuctionRequest auctionRequest, AuctionType type) {
        this.price = auctionRequest.getPrice();
        this.type = type;
        setDueDate(auctionRequest.getDueDays());
        this.state = AuctionStateFactory.createState(this);
    }

    private void setDueDate(long dueDays) {
        this.dueDate = LocalDateTime.of(LocalDate.now().plusDays(dueDays + 1L),
                LocalTime.MIDNIGHT);
    }

    /**
     * 입찰 내용을 변경한다.
     *
     * @param price   새로 적용할 가격
     * @param dueDays 입찰 만료일
     */
    public void update(BigDecimal price, long dueDays) {
        state.update(this);

        this.price = price;
        setDueDate(dueDays);
    }

    /**
     * 입찰 완료 처리한다.
     */
    public void sign(User bidder) {
        if (Objects.equals(this.getUser().getId(), bidder.getId())) {
            throw new IllegalArgumentException("직접 등록한 입찰에 참여할 수 없습니다.");
        }
        state.finish(this);

        this.signedAt = LocalDateTime.now();
        this.bidder = bidder;
    }

    /**
     * 입찰 취소 처리한다.
     */
    public void cancel() {
        state.cancel(this);

        this.canceledAt = LocalDateTime.now();
    }

    @NoArgsConstructor(access = AccessLevel.NONE)
    private static final class AuctionStateFactory {

        private static final Predicate<Auction> ACTIVE
                = auction -> auction.dueDate.isAfter(LocalDateTime.now());

        public static AuctionState createState(Auction auction) {
            if (auction.canceledAt != null) {
                return new Canceled();
            }

            if (auction.signedAt != null) {
                return new Finished();
            }

            return ACTIVE.test(auction) ? new Active() : new Inactive();
        }

    }


}
