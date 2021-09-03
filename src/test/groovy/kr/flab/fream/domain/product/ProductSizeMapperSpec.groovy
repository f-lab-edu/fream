package kr.flab.fream.domain.product

import kr.flab.fream.DatabaseTest
import kr.flab.fream.mybatis.mapper.product.BrandMapper
import kr.flab.fream.mybatis.mapper.product.ProductMapper
import kr.flab.fream.mybatis.mapper.product.ProductSizeMapper
import kr.flab.fream.mybatis.mapper.product.SizeMapper
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

import static kr.flab.fream.domain.product.BrandMapperSpec.brand
import static kr.flab.fream.domain.product.ProductMapperSpec.sneakers
import static kr.flab.fream.domain.product.SizeMapperSpec.us85Size
import static kr.flab.fream.domain.product.SizeMapperSpec.us8Size

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductSizeMapperSpec extends DatabaseTest {

    @Autowired
    ProductSizeMapper productSizeMapper

    @Autowired
    ProductMapper productMapper

    @Autowired
    SizeMapper sizeMapper

    @Autowired
    BrandMapper brandMapper

    def "map sizes to product"() {
        given:
        def nike = brand()
        def size = us8Size()
        def size2 = us85Size()
        def sizes = new Sizes(Arrays.asList(size, size2))
        def sneakers = sneakers(nike, sizes)

        sizeMapper.addSize(size)
        sizeMapper.addSize(size2)
        brandMapper.addBrand(nike)
        productMapper.addProduct(sneakers)

        expect:
        productSizeMapper.mapSizesToProduct(sneakers) == 2
    }

}
