package kr.flab.fream.controller.auction;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 입찰 수정 입력을 표현한 DTO 클래스.
 *
 * @since 0.2.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AuctionPatchRequest {

    @Min(0)
    BigDecimal price;

    @Min(1)
    @Max(60)
    Long dueDays;

}
