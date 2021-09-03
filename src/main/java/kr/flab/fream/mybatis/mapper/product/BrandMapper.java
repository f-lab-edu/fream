package kr.flab.fream.mybatis.mapper.product;

import java.util.List;
import kr.flab.fream.domain.product.Brand;
import org.apache.ibatis.annotations.Mapper;

/**
 * {@link Brand} 클래스의 매퍼.
 *
 * @since 1.0.0
 */
@Mapper
public interface BrandMapper {

    int addBrand(Brand brand);

    int addBrands(List<Brand> brands);

}
