package kr.flab.fream.domain.product;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 상품의 발매 정보를 나타낸 클래스.
 * <br>
 * 발매 정보에는 브랜드에서 발행한 상품 코드, 발매일, 출시 당시의 가격이 포함된다. 각 정보는 브랜드에서 제공하지 않거나 정보를 알아낼 수 없을 수 있기 때문에 {@code
 * null} 을 값으로 가질 수 있다.
 *
 * @author Jake
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDetails {

    private String productCode;
    private LocalDate releaseDate;
    private Long retailPrice;

}
