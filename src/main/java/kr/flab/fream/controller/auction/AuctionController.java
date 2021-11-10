package kr.flab.fream.controller.auction;

import javax.validation.Valid;
import kr.flab.fream.domain.auction.model.Auction;
import kr.flab.fream.domain.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final ModelMapper modelMapper;

    /**
     * 입찰 생성 API.
     *
     * @param request 입찰 정보
     * @return 생성된 입찰 정보를 반환
     */
    @PostMapping(value = {"/asks", "bids"})
    public AuctionDto createAuction(@Valid @RequestBody AuctionRequest request) {
        Auction auction = service.createAuction(request);
        return modelMapper.map(auction, new TypeToken<AuctionDto>() {
        }.getType());
    }

}
