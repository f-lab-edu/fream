package kr.flab.fream.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품의 사이즈 하나를 표현한 클래스.
 * <br>
 * 사이즈는 카테고리, 국가, 성별에 따라 표기법이 천차만별이며 새로운 사이즈 표기법을 사용하는 경우도 종종 있기 때문에 사이즈의 이름에 제약을 두지 않는다.
 *
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Size {

    private Long id;
    private String name;

}
