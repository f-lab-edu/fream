package kr.flab.fream.domain.product.model;

import lombok.Value;

/**
 * 상품의 브랜드를 나타내는 클래스.
 *
 * @since 1.0
 */
@Value
public class Brand {

    Long id;
    String name;
    String englishName;

}
