package kr.flab.fream.domain.product;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * 검색에 사용할 키워드.
 * <br>
 * <br>
 * 검색에는 N-Gram 기반의 MySQL Full-Text Index 를 사용하고 있으며, 크기를 2로 제한하고 있다. 때문에 사용자가 입력하는 키워드는 최소 두 글자
 * 이상이어야 한다.
 *
 * @since 1.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Keyword {

    private static final int MINIMUM_LENGTH = 2;

    private final String keyword;

    public static Keyword of(String str) {
        validate(str);
        return new Keyword(str);
    }

    private static void validate(String str) {
        if (str == null || str.length() < MINIMUM_LENGTH) {
            throw new IllegalArgumentException("검색 키워드는 두 글자 이상이어야 합니다.");
        }
    }

}
