package kr.flab.fream.domain.product;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 상품의 사이즈 목록을 나타낸 클래스.
 *
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class Sizes {

    private List<Size> sizeList = new ArrayList<>();

}