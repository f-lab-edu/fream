package kr.flab.fream.controller.auction;

import java.math.BigDecimal;
import kr.flab.fream.domain.auction.model.AuctionType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 상품 상세 페이지에 입찰 목록을 노출하는데, 그 곳에 표시될 요약 목록의 요소를 나타낸다.
 *
 * @author Jake
 * @since 1.0.0
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class AuctionSummaryByPriceAndSizeWithQuantity {

    /**
     * 사이즈 이름.
     */
    String size;

    /**
     * 입찰가.
     */
    BigDecimal price;

    /**
     * 어떤 사이즈의 특정 입찰가로 올라온 입찰의 총 개수.
     */
    long quantity;

    /**
     * 입찰 타입.
     */
    AuctionType type;

}
