package kr.flab.fream.domain.auction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.product.model.Size;
import kr.flab.fream.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 판매 입찰, 구매 입찰의 공통 부분을 지칭하는 모델.
 *
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Auction {

    private Long id;
    @Setter
    private BigDecimal price;
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime dueDate;
    private Product product;
    private Size size;
    private User user;
    private AuctionType type;
    private LocalDateTime canceledAt;
    private LocalDateTime signedAt;

}
