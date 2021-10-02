package kr.flab.fream.mybatis.util;

import javax.annotation.Nullable;
import org.apache.ibatis.session.RowBounds;

/**
 * Mybatis 의 {@link RowBounds} 에 validation 을 추가한 클래스.
 * <br>
 * 페이지 당 fetch 할 데이터의 개수는 지정하지 않으면 25 개이다.
 *
 * @since 1.0.0
 */
public class ExtendedRowBounds extends RowBounds {

    private static final int DEFAULT_LIMIT = 10;

    private ExtendedRowBounds(int offset, int limit) {
        super(offset, limit);
    }

    /**
     * 페이지 번호를 바탕으로 {@link RowBounds} 를 생성한다. 이 때 적용되는 LIMIT 의 기본 값은 10이다.
     * <br>
     * 페이지 번호를 입력하지 않았다면 첫 페이지 조회를 요청했다고 간주한다.
     *
     * @param page 페이지 번호. 값은 {@code 1} 이상이거나, {@code null} 이어야 한다.
     * @return 페이지 번호에 해당하는 {@link RowBounds} 생성
     */
    public static ExtendedRowBounds of(@Nullable Integer page) {
        if (page == null) {
            return createDefault();
        }

        if (page <= 0) {
            throw new IllegalArgumentException("페이지 번호는 0보다 커야 합니다.");
        }

        return ExtendedRowBounds.of((page - 1) * DEFAULT_LIMIT, DEFAULT_LIMIT);
    }

    private static ExtendedRowBounds of(int offset, int limit) {
        return new ExtendedRowBounds(offset, limit);
    }

    /**
     * {@code ExtendedRowBounds(1)} 코드를 작성한 것과 같다.
     *
     * @return 첫 번째 페이지를 나타내는 RowBounds 반환
     */
    public static ExtendedRowBounds firstPage() {
        return of(1);
    }

    private static ExtendedRowBounds createDefault() {
        return ExtendedRowBounds.of(0, DEFAULT_LIMIT);
    }

}
