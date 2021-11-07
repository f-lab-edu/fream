package kr.flab.fream.controller.auction;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 입찰 생성 입력을 표현한 DTO 클래스.
 *
 * @since 0.2.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AuctionRequest {

    @Min(0)
    @NotNull
    BigDecimal price;
    @NotNull
    Long productId;
    @NotNull
    Long sizeId;
    @NotNull
    Long userId;
    @Min(1)
    @NotNull
    Long dueDays;

}
