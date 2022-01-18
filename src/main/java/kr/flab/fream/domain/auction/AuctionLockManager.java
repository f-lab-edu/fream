package kr.flab.fream.domain.auction;

import kr.flab.fream.mybatis.mapper.auction.AuctionLockByProductIdAndSizeIdMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 입찰 생성, 입찰 성사 시 별도의 테이블을 사용하여 락 기능을 제공한다.
 *
 * @since 1.0.1
 */
@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class AuctionLockManager {

    private final AuctionLockByProductIdAndSizeIdMapper lockMapper;

    public void lock(Long productId, Long sizeId) {
        lockMapper.tryInsertRecord(productId, sizeId);
        lockMapper.getLock(productId, sizeId);
    }

}
