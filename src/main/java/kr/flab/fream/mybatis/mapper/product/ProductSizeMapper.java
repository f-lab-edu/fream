package kr.flab.fream.mybatis.mapper.product;

import kr.flab.fream.domain.product.Product;
import kr.flab.fream.domain.product.Size;
import org.apache.ibatis.annotations.Mapper;

/**
 * {@link Product} 클래스와 {@link Size} 클래스 사이의 Many-to-Many 관계를 저장하는 테이블의 Mapper 인터페이스.
 *
 * @since 1.0.0
 */
@Mapper
public interface ProductSizeMapper {

    int mapSizesToProduct(Product product);

}
