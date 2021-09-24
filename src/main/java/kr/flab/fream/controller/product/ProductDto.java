package kr.flab.fream.controller.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * {@link kr.flab.fream.domain.product.model.Product} 도메인 클래스의 DTO.
 * <br>
 * 주로 상품 목록을 노출할 때 사용한다.
 *
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductDto {

    Long id;
    String name;
    String englishName;
    String category;
    String brandName;
    String brandEnglishName;

}
