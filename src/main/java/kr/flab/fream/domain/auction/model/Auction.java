package kr.flab.fream.domain.auction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.product.model.Size;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 판매 입찰, 구매 입찰의 공통 부분을 지칭하는 모델.
 *
 * @since 1.0.0
 */
@NoArgsConstructor
@Setter
public class Auction {

    protected Long id;
    protected BigDecimal price;
    protected Product product;
    protected Size size;
    protected LocalDateTime createdAt;
    protected LocalDateTime dueDate;

}
