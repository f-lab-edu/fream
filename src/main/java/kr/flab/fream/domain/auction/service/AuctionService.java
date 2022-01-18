package kr.flab.fream.domain.auction.service;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Nullable;
import kr.flab.fream.controller.auction.AuctionDto;
import kr.flab.fream.controller.auction.AuctionPatchRequest;
import kr.flab.fream.controller.auction.AuctionRequest;
import kr.flab.fream.controller.auction.AuctionSummaryByPriceAndSizeWithQuantity;
import kr.flab.fream.domain.auction.AuctionLockManager;
import kr.flab.fream.domain.auction.AuctionSearchOption;
import kr.flab.fream.domain.auction.dto.SignAuctionResponse;
import kr.flab.fream.domain.auction.model.Auction;
import kr.flab.fream.domain.auction.model.AuctionType;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.product.model.Size;
import kr.flab.fream.domain.product.service.ProductService;
import kr.flab.fream.domain.user.model.User;
import kr.flab.fream.mybatis.mapper.auction.AuctionMapper;
import kr.flab.fream.mybatis.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 입찰 도메인의 서비스.
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuctionService {

    private final AuctionMapper auctionMapper;
    private final UserMapper userMapper;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final AuctionLockManager lockManager;

    /**
     * 입찰 목록을 조회한다.
     * <br>
     * 구매 입찰은 내림차순으로, 판매 입찰은 오름차순으로 정렬되며 가격이 같다면 id 기준 오름차순으로
     * 정렬하여 결과를 반환한다.
     *
     * @param searchOption 조회 시 사용할 옵션
     * @return 입찰 목록 반환
     */
    @Transactional(readOnly = true)
    public List<AuctionDto> getAuctions(AuctionSearchOption searchOption) {
        List<Auction> auctions = auctionMapper.getAuctions(searchOption);

        return modelMapper.map(auctions, new TypeToken<List<Auction>>(){}.getType());
    }

    /**
     * 구매 혹은 판매 입찰의 가격별 수량 조회.
     *
     * @param type      입찰 타입
     * @param productId 조회할 상품 번호
     * @param sizeId    조회할 상품의 특정 사이즈 번호
     * @param lastPrice 마지막으로 조회했던 가격
     * @return 사이즈별 가격과 수량을 반환
     */
    @Transactional(readOnly = true)
    public List<AuctionSummaryByPriceAndSizeWithQuantity> getAuctionSummaries(AuctionType type,
            Long productId, @Nullable Long sizeId, @Nullable BigDecimal lastPrice) {
        return auctionMapper.getAuctionSummaries(type, productId, sizeId, lastPrice);
    }

    /**
     * 구매 입찰을 생성한다.
     *
     * @param request 유저가 입력한 입찰 정보.
     * @return 입찰을 생성한 뒤 반환.
     */
    public AuctionDto createAuction(AuctionRequest request) {
        lockManager.lock(request.getProductId(), request.getSizeId());

        Auction auction = request.getType().constructor.apply(request);

        Product product = productService.getProduct(request.getProductId());
        Size size = product.getSize(request.getSizeId());
        User user = userMapper.getUserById(request.getUserId());

        auction.setProduct(product);
        auction.setSize(size);
        auction.setUser(user);

        auctionMapper.create(auction);

        return convert(auction);
    }

    /**
     * 특정 입찰의 가격과 만료 일자를 변경한다.
     *
     * @param auctionId 입찰 ID
     * @param request   입찰 변경 요청 값
     * @return 변경 결과를 반환
     */
    public AuctionDto update(Long auctionId, AuctionPatchRequest request) {
        Auction auction = auctionMapper.getAuction(auctionId);
        auction.update(request.getPrice(), request.getDueDays());

        auctionMapper.update(auction);

        return convert(auction);
    }

    /**
     * 입찰을 취소한다.
     *
     * @param auctionId 취소할 입찰의 ID
     */
    public void cancel(Long auctionId) {
        Auction auction = auctionMapper.getAuction(auctionId);
        auction.cancel();

        auctionMapper.update(auction);
    }

    /**
     * 입찰을 체결한다.
     *
     * @param bidder    낙찰받은 유저
     * @param auctionId 입찰 ID
     */
    public SignAuctionResponse sign(User bidder, Long auctionId) {
        Auction auction = auctionMapper.getAuction(auctionId);

        lockManager.lock(auction.getProduct().getId(), auction.getSize().getId());

        return signHelper(bidder, auctionId);
    }

    private SignAuctionResponse signHelper(User bidder, Long auctionId) {
        Auction auction = auctionMapper.getAuctionForUpdate(auctionId);

        auction.sign(bidder);
        auctionMapper.update(auction);

        return modelMapper.map(auction, SignAuctionResponse.getTypeObject());
    }

    private AuctionDto convert(Auction auction) {
        return modelMapper.map(auction, AuctionDto.getTypeObject());
    }

}
