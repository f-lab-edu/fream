package kr.flab.fream.mybatis.mapper.product

import kr.flab.domain.product.BrandFixtures
import kr.flab.domain.product.ProductFixtures
import kr.flab.domain.product.SizeFixtures
import kr.flab.fream.DatabaseTest
import kr.flab.fream.domain.product.model.Sizes
import kr.flab.fream.mybatis.mapper.product.BrandMapper
import kr.flab.fream.mybatis.mapper.product.ProductMapper
import kr.flab.fream.mybatis.mapper.product.ProductSizeMapper
import kr.flab.fream.mybatis.mapper.product.SizeMapper
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

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
        def brand = BrandFixtures.createBrand()
        def size = SizeFixtures.createUs8Size()
        def size2 = SizeFixtures.createUs85Size()
        def sizes = new Sizes(Arrays.asList(size, size2))
        def sneakers = ProductFixtures.createSneakers(brand, sizes)

        sizeMapper.addSize(size)
        sizeMapper.addSize(size2)
        brandMapper.addBrand(brand)
        productMapper.addProduct(sneakers)

        expect:
        productSizeMapper.mapSizesToProduct(sneakers) == 2
    }

}
