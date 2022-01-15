package kr.flab.fream.mybatis.mapper.auction;

import org.apache.ibatis.annotations.Mapper;

/**
 * 입찰 생성, 거래 성사 시 사용할 락 테이블을 조작하는 mapper.
 * <br>
 * productId, sizeId 기준으로 락을 건다.
 *
 * @since 1.0.1
 */
@Mapper
public interface AuctionLockByProductIdAndSizeIdMapper {

    void tryInsertRecord(Long productId, Long sizeId);

    void getLock(Long productId, Long sizeId);

}
