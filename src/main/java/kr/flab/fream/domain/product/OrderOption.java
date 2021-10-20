package kr.flab.fream.domain.product;


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
    RECENTLY_RELEASED

}
