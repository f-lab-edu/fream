package kr.flab.fream.domain.product.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * 상품의 카태고리. 카테고리는 계층 구조를 가진다.
 *
 * @since 1.0
 */
public enum Category {

    SNEAKERS(null, "스니커즈"),

    CLOTHING(null, "의류"),
    OUTER(CLOTHING, "아우터"),
    TOP(CLOTHING, "상의"),
    BOTTOM(CLOTHING, "하의"),

    ACCESSORIES(null, "패션 잡화"),
    HAT(ACCESSORIES, "모자"),
    BAG(ACCESSORIES, "가방"),
    WALLET(ACCESSORIES, "지갑"),
    ACCESSORIES_ETC(ACCESSORIES, "기타"),

    TECH(null, "테크"),
    GRAPHIC_CARD(TECH, "그래픽 카드"),
    GAMING_CONSOLE(TECH, "게임기"),
    TECH_ETC(TECH, "기타"),

    LIFE(null, "라이프");

    private final Category parent;
    private final String name;

    private final Set<Category> children;

    Category(Category parent, String name) {
        this.parent = parent;
        this.name = name;
        this.children = new HashSet<>();
        setChildren(this.parent);
    }

    private void setChildren(Category parent) {
        if (this.parent != null) {
            this.parent.children.add(this);
        }
    }

    /**
     * 카테고리 문자열로부터 enum 을 반환하는 팩토리 메소드.
     *
     * @param categoryString 카테고리 enum 에 대응되는 문자열(case-insensitive).
     * @return 문자열에 대응하는 카테고리 enum 을 반환한다.
     * @since 1.0.0
     */
    public static Category of(String categoryString) {
        return Arrays.stream(Category.values())
                .filter(category -> category.name().equalsIgnoreCase(categoryString))
                .findAny()
                .orElseThrow();
    }

    /**
     * 카테고리에 속한 모든 하위 카테고리를 가져온다.
     *
     * @return 대상 카테고리 아래의 모든 하위 카테코리의 Set 반환
     * @since 1.0.0
     */
    public Set<Category> getAllChildren() {
        Set<Category> res = new HashSet<>();

        for (final var child : this.children) {
            getAllChildrenHelper(res, child);
            res.add(child);
        }

        return res;
    }

    private void getAllChildrenHelper(Set<Category> container, Category currentCategory) {
        for (final var child : currentCategory.children) {
            getAllChildrenHelper(container, child);
            container.add(child);
        }
    }

}
