package kr.flab.fream.domain.auction;

import java.math.BigDecimal;
import javax.annotation.Nonnull;
import kr.flab.fream.domain.auction.model.AuctionType;
import lombok.Builder;

/**
 * 입찰 검색 옵션. Pagination 에는 이전 입찰 Id와 가격이 필요하다.
 *
 * @author Jake
 * @since 1.0.0
 */
@Builder
public class AuctionSearchOption {

    @Nonnull
    private final AuctionType auctionType;
    @Nonnull
    private final Long productId;
    private final Long sizeId;
    private final Long lastAuctionId;
    private final BigDecimal lastPrice;
    @Builder.Default
    private final Integer items = 10;

    /**
     * 빌더 클래스에 추가한 입력 값 검증 메소드를 실행할 수 있게 build 메소드를 수정함.
     *
     * @return 검색 옵션 객체 반환
     */
    public static AuctionSearchOptionBuilder builder() {
        return new AuctionSearchOptionBuilder() {
            @Override
            public AuctionSearchOption build() {
                this.prebuild();
                return super.build();
            }
        };
    }

    /**
     * Lombok 이 생성할 빌더 클래스에 객체 생성 전 입력 값 겅증을 위한 메소드 추가.
     */
    public static class AuctionSearchOptionBuilder {

        void prebuild() {
            if (this.lastPrice != null ^ this.lastAuctionId != null) {
                throw new IllegalArgumentException("페이지 조회에 필요한 값의 일부가 없습니다.");
            }
        }

    }

}
