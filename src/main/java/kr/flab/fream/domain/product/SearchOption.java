package kr.flab.fream.domain.product;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import kr.flab.fream.domain.product.model.Category;
import kr.flab.fream.mybatis.util.ExtendedRowBounds;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;

/**
 * 검색 옵션을 묶은 클래스.
 *
 * @since 1.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SearchOption {

    private final Keyword keyword;
    private final RowBounds rowBounds;
    private final Set<Category> categories;

    /**
     * 유저로부터 입력받은 키워드, 페이지 번호를 {@code SearchOption} 클래스로 묶어 반환.
     *
     * @param keywordString 유저가 입력한 검색 키워드
     * @param page          검색할 페이지
     * @param categoryStr   상품 카테고리
     * @return 입력 값을 검증한 다음, 서비스 레이어에서 사용할 수 있는 {@link SearchOption} 을 리턴
     */
    public static SearchOption of(@Nullable String keywordString, @Nullable Integer page,
            @Nullable String categoryStr) {
        return new SearchOptionBuilder()
                .keyword(keywordString)
                .rowBounds(page)
                .categories(categoryStr)
                .build();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class SearchOptionBuilder {

        private Keyword keyword;
        private RowBounds rowBounds;
        private Set<Category> categories = new HashSet<>();

        private SearchOptionBuilder keyword(String keywordString) {
            this.keyword = Keyword.of(keywordString);
            return this;
        }

        private SearchOptionBuilder rowBounds(Integer page) {
            this.rowBounds = ExtendedRowBounds.of(page);
            return this;
        }

        private SearchOptionBuilder categories(String categoryString) {
            if (categoryString == null) {
                return this;
            }

            final var category = Category.of(categoryString);
            this.categories = new HashSet<>(category.getAllChildren());
            this.categories.add(category);

            return this;
        }

        private SearchOption build() {
            return new SearchOption(this.keyword, this.rowBounds, this.categories);
        }
    }

}
