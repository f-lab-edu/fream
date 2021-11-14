package kr.flab.fream.domain.auction.service;

import kr.flab.fream.controller.auction.AuctionRequest;
import kr.flab.fream.domain.auction.model.Auction;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.product.model.Size;
import kr.flab.fream.domain.product.service.ProductService;
import kr.flab.fream.domain.user.model.User;
import kr.flab.fream.domain.user.service.UserService;
import kr.flab.fream.mybatis.mapper.auction.AuctionMapper;
import lombok.RequiredArgsConstructor;
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
    private final UserService userService;
    private final ProductService productService;

    /**
     * 구매 입찰을 생성한다.
     *
     * @param request 유저가 입력한 입찰 정보.
     * @return 입찰을 생성한 뒤 반환.
     */
    public Auction createAuction(AuctionRequest request) {
        Auction auction = request.getType().constructor.apply(request);

        Product product = productService.getProduct(request.getProductId());
        Size size = product.getSize(request.getSizeId());
        User user = userService.getUser(request.getUserId());

        auction.setProduct(product);
        auction.setSize(size);
        auction.setUser(user);

        auctionMapper.create(auction);

        return auction;
    }

}
