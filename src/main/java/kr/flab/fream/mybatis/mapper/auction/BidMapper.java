package kr.flab.fream.mybatis.mapper.auction;

import kr.flab.fream.domain.auction.model.Bid;
import org.apache.ibatis.annotations.Mapper;

/**
 * {@link kr.flab.fream.domain.auction.model.Bid} 의 mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface BidMapper {

    int create(Bid bid);

    int cancel(Bid bid);

    int update(Bid bid);

}
