package kr.flab.fream.domain.product;


import java.util.List;
import javax.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;

/**
 * 검색에 사용할 정렬 옵션.
 *
 * @since 1.0.0
 */
public enum OrderOption {

    /**
     * 화면 상에 <b>인기순</b> 으로 노출되는 정렬 옵션이다. 상품의 상세 페이지가 가장 많이 노출된 순서로 정렬한다.
     */
    POPULAR,

    /**
     * 화면 상에 <b>발매일순</b> 으로 노출되는 정렬 옵션이다. 가장 최근에 발매된 순서로 상품 목록을 정렬한다.
     */
    RECENTLY_RELEASED;

    /**
     * 문자열을 {@link OrderOption} 로 변환해준다.
     * <br>
     * 대표적으로 {@link kr.flab.fream.controller.product.ProductController#search(String, Integer,
     * String, List, List, OrderOption)} 에 쓰인다.
     * <br>
     * 해당 클래스는 {@link kr.flab.fream.config.FormattingConfiguration} 에 등록됐다.
     *
     * @since 1.0.0
     */
    public static class OrderOptionConverter implements Converter<String, OrderOption> {

        @Override
        public OrderOption convert(@Nullable String source) {
            try {
                return OrderOption.valueOf(source);
            } catch (Exception e) {
                // 예외를 던지면 MethodArgumentTypeMismatchException 이 발생하기 때문에 null 리턴
                return null;
            }
        }
    }

}
