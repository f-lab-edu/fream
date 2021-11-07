package kr.flab.fream.controller.auction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import kr.flab.fream.controller.product.ProductDto;
import kr.flab.fream.domain.auction.model.AuctionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * {@link kr.flab.fream.domain.auction.model.Ask}와 {@link kr.flab.fream.domain.auction.model.Bid}의
 * DTO 클래스.
 *
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AuctionDto {

    Long id;
    BigDecimal price;
    LocalDateTime dueDate;
    ProductDto product;
    String sizeName;
    AuctionType type;

}
