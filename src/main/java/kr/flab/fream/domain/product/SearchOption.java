package kr.flab.fream.domain.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import kr.flab.fream.domain.product.model.Category;
import kr.flab.fream.mybatis.util.ExtendedRowBounds;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;

/**
 * 검색 옵션을 묶은 클래스.
 *
 * @since 1.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public final class SearchOption {

    private final Keyword keyword;
    private final RowBounds rowBounds;
    private final Set<Category> categories;
    private final List<Long> brandIdList;
    private final List<Long> sizeIdList;
    private final OrderOption orderOption;

    /**
     * 유저로부터 입력받은 키워드, 페이지 번호를 {@code SearchOption} 클래스로 묶어 반환.
     *
     * @param keywordString 유저가 입력한 검색 키워드
     * @param page          검색할 페이지
     * @param categoryStr   상품 카테고리
     * @param brandIdList   브랜드 ID 목록
     * @param sizeIdList    사이즈 ID 목록
     * @param orderOption   검색 옵션
     * @return 입력 값을 검증한 다음, 서비스 레이어에서 사용할 수 있는 {@link SearchOption} 을 리턴
     */
    public static SearchOption of(@Nullable String keywordString, @Nullable Integer page,
            @Nullable String categoryStr, @Nullable List<Long> brandIdList,
            @Nullable List<Long> sizeIdList, @Nullable OrderOption orderOption) {
        return builder().keyword(keywordString)
                .page(page)
                .categoriesOf(categoryStr)
                .brandIdList(brandIdList)
                .sizeIdList(sizeIdList)
                .orderOption(orderOption)
                .build();
    }

    public static SearchOptionBuilder builder() {
        return new SearchOptionBuilder();
    }

    static class SearchOptionBuilder {

        private Keyword keyword = Keyword.emptyKeyword();
        private RowBounds rowBounds = ExtendedRowBounds.firstPage();
        private Set<Category> categories = new HashSet<>();
        private List<Long> brandIdList = new ArrayList<>();
        private List<Long> sizeIdList = new ArrayList<>();
        private OrderOption orderOption = OrderOption.POPULAR;

        private SearchOptionBuilder() {
        }

        public SearchOptionBuilder keyword(String keywordString) {
            this.keyword = Keyword.of(keywordString);
            return this;
        }

        public SearchOptionBuilder page(Integer page) {
            return rowBounds(ExtendedRowBounds.of(page));
        }

        // Hide rowBounds field
        private SearchOptionBuilder rowBounds(RowBounds rowBounds) {
            this.rowBounds = rowBounds;
            return this;
        }

        public SearchOptionBuilder categoriesOf(String categoryString) {
            if (categoryString == null) {
                return this;
            }

            final var category = Category.of(categoryString);
            final var categorySet = new HashSet<>(category.getAllChildren());
            categorySet.add(category);

            return categories(categorySet);
        }

        // Hide categories field
        private SearchOptionBuilder categories(Set<Category> categories) {
            this.categories = categories;
            return this;
        }

        public SearchOptionBuilder brandIdList(List<Long> brandIdList) {
            if (brandIdList == null) {
                return this;
            }

            this.brandIdList = brandIdList;
            return this;
        }

        public SearchOptionBuilder sizeIdList(List<Long> sizeIdList) {
            if (sizeIdList == null) {
                return this;
            }

            this.sizeIdList = sizeIdList;
            return this;
        }

        public SearchOptionBuilder orderOption(OrderOption orderOption) {
            if (orderOption == null) {
                return this;
            }

            this.orderOption = orderOption;
            return this;
        }

        public SearchOption build() {
            return new SearchOption(this.keyword, this.rowBounds, this.categories,
                    this.brandIdList, this.sizeIdList, this.orderOption);
        }
    }

}
