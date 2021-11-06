package kr.flab.fream.domain.product.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품의 사이즈 목록을 나타낸 클래스.
 *
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Sizes {

    private List<Size> sizeList = new ArrayList<>();

    public Size getSize(long id) {
        return this.sizeList.stream().filter(size -> size.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 ID와 일치하는 사이즈가 없습니다."));
    }

}
