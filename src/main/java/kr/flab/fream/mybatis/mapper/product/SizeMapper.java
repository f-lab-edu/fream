package kr.flab.fream.mybatis.mapper.product;

import java.util.List;
import kr.flab.fream.domain.product.Size;
import org.apache.ibatis.annotations.Mapper;

/**
 * {@link Size} 클래스의 매퍼.
 *
 * @since 1.0.0
 */
@Mapper
public interface SizeMapper {

    int addSize(Size size);

    int addSizes(List<Size> sizes);

}
