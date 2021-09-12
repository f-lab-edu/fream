package kr.flab.fream.mybatis.mapper.product;

import java.util.List;
import kr.flab.fream.domain.product.Keyword;
import kr.flab.fream.domain.product.model.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * {@link Product} 클래스의 매퍼.
 *
 * @since 1.0.0
 */
@Mapper
public interface ProductMapper {

    Product getProductById(Long id);

    int addProduct(Product product);

    List<Product> search(Keyword keyword);

}
