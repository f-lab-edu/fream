package kr.flab.fream.domain.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품을 나타낸 클래스.
 * <br>
 * 각 상품은 고유한 이름과 기본 정보({@link ProductDetails}), 브랜드({@link Brand}), 카테고리({@link Category}, 그리고 하나
 * 이상의 사이즈({@link Sizes}) 로 구성된다.
 * <br>
 *
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Product {

    private Long id;
    private String name;
    private String englishName;
    private Category category;
    private ProductDetails details;
    private Brand brand;
    private Sizes sizes;

}
