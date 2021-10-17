package kr.flab.fream.controller.product;

import java.util.List;
import kr.flab.fream.domain.product.SearchOption;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Product 컨트롤러.
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    /**
     * 상품 목록을 조회한다. 유저는 상품 검색에 옵션을 사용해 원하는 정보만 가져올 수 있다.
     *
     * @param keyword     유저가 입력한 상품 키워드
     * @param page        조죄할 페이지
     * @param category    카테고리를 나타내는 문자열
     * @param brandIdList 브랜드 ID 목록
     * @param sizeIdList  사이즈 ID 목록
     * @return 상품 목록 리턴
     */
    @GetMapping
    public List<ProductDto> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<Long> brandIdList,
            @RequestParam(required = false) List<Long> sizeIdList
    ) {
        final var searchOption =
                SearchOption.of(keyword, page, category, brandIdList, sizeIdList);

        List<Product> productList = productService.search(searchOption);

        return modelMapper.map(productList, new TypeToken<List<ProductDto>>() {
        }.getType());
    }

}
