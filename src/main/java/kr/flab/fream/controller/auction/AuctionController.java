package kr.flab.fream.controller.auction;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import kr.flab.fream.auth.Authentication;
import kr.flab.fream.domain.auction.AuctionSearchOption;
import kr.flab.fream.domain.auction.dto.SignAuctionResponse;
import kr.flab.fream.domain.auction.model.AuctionType;
import kr.flab.fream.domain.auction.service.AuctionService;
import kr.flab.fream.domain.user.model.User;
import kr.flab.fream.mybatis.util.exception.NoAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 입찰 도메인의 컨트롤러.
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService service;

    @GetMapping("/asks")
    public List<AuctionDto> getAsks(
            @RequestParam @Valid @NotNull Long productId,
            @RequestParam(required = false) Long sizeId,
            @RequestParam(required = false) Long lastAuctionId,
            @RequestParam(required = false) BigDecimal lastPrice,
            @RequestParam(required = false) Integer items
    ) {
        return getAuctions(AuctionType.ASK, productId, sizeId, lastAuctionId, lastPrice, items);
    }

    @GetMapping("/bids")
    public List<AuctionDto> getBids(
            @RequestParam @Valid @NotNull Long productId,
            @RequestParam(required = false) Long sizeId,
            @RequestParam(required = false) Long lastAuctionId,
            @RequestParam(required = false) BigDecimal lastPrice,
            @RequestParam(required = false) Integer items
    ) {
        return getAuctions(AuctionType.BID, productId, sizeId, lastAuctionId, lastPrice, items);
    }

    private List<AuctionDto> getAuctions(AuctionType type, Long productId, Long sizeId,
            Long lastAuctionId, BigDecimal lastPrice, Integer items) {
        AuctionSearchOption searchOption = AuctionSearchOption.builder()
                .auctionType(type)
                .productId(productId)
                .sizeId(sizeId)
                .lastAuctionId(lastAuctionId)
                .lastPrice(lastPrice)
                .items(items)
                .build();

        return service.getAuctions(searchOption);
    }

    @GetMapping("/asks/summaries")
    public List<AuctionSummaryByPriceAndSizeWithQuantity> getAskSummaries(
            @RequestParam @Valid @NotNull Long productId,
            @RequestParam(required = false) Long sizeId,
            @RequestParam(required = false) BigDecimal lastPrice
    ) {
        return service.getAuctionSummaries(AuctionType.ASK, productId, sizeId, lastPrice);
    }

    @GetMapping("/bids/summaries")
    public List<AuctionSummaryByPriceAndSizeWithQuantity> getBidSummaries(
            @RequestParam @Valid @NotNull Long productId,
            @RequestParam(required = false) Long sizeId,
            @RequestParam(required = false) BigDecimal lastPrice
    ) {
        return service.getAuctionSummaries(AuctionType.BID, productId, sizeId, lastPrice);
    }

    /**
     * 입찰 생성 API.
     *
     * @param request 입찰 정보
     * @return 생성된 입찰 정보를 반환
     */
    @PostMapping(value = {"/asks", "/bids"})
    @ResponseStatus(HttpStatus.CREATED)
    public AuctionDto createAuction(@Valid @RequestBody AuctionRequest request) {
        return service.createAuction(request);
    }

    /**
     * 입찰 수정 API.
     *
     * @param request 수정할 내용이 포함된 입찰 정보
     * @return 수정된 입찰 정보를 반환
     */
    @PatchMapping(value = {"/asks/{id}", "/bids/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public AuctionDto modifyAuction(
            @Valid @PathVariable @NotNull Long id,
            @Valid @RequestBody AuctionPatchRequest request) {
        return service.update(id, request);
    }

    /**
     * 등록한 입찰을 취소한다.
     *
     * @param id 입찰 ID
     */
    @DeleteMapping(value = {"/asks/{id}", "/bids/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelAuction(
            @Valid @PathVariable @NotNull Long id) {
        service.cancel(id);
    }

    /**
     * 등록된 구매 또는 판매 입찰을 유저에게 낙찰한다.
     *
     * @param user 낙찰받은 유저
     * @param id   입찰 ID
     * @return 입찰 ID와 낙찰 시간을 반환
     */
    @PostMapping(value = {"/asks/{id}/sign", "/bids/{id}/sign"})
    public SignAuctionResponse signAuction(
            @Authentication User user,
            @Valid @PathVariable @NotNull Long id) {
        if (user == null) {
            throw new NoAuthenticationException();
        }

        return service.sign(user, id);
    }

}
