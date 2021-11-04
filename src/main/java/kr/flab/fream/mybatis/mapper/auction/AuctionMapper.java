package kr.flab.fream.mybatis.mapper.auction;

import kr.flab.fream.domain.auction.model.Auction;
import org.apache.ibatis.annotations.Mapper;

/**
 * {@link kr.flab.fream.domain.auction.model.Auction} 의 mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface AuctionMapper {

    int create(Auction auction);

    int cancel(Auction auction);

    int update(Auction auction);

    Auction getAuction(Long id);

}
