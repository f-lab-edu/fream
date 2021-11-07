package kr.flab.fream.domain.product.service;

import java.util.List;
import kr.flab.fream.domain.product.SearchOption;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.mybatis.mapper.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 상품 서비스의 Concrete Class.
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    public List<Product> search(SearchOption searchOption) {
        return productMapper.search(searchOption);
    }

    public Product getProduct(Long id) {
        return productMapper.getProductById(id);
    }

}
