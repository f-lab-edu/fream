package kr.flab.fream.mybatis.mapper.product

import kr.flab.domain.product.BrandFixtures
import kr.flab.domain.product.SizeFixtures
import kr.flab.fream.DatabaseTest
import kr.flab.fream.domain.product.OrderOption
import kr.flab.fream.domain.product.SearchOption
import kr.flab.fream.domain.product.model.Category
import kr.flab.fream.domain.product.model.Sizes
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import spock.lang.Specification

import java.util.stream.Collectors
import java.util.stream.Stream

import static kr.flab.domain.product.ProductFixtures.*
import static org.assertj.core.api.Assertions.assertThat

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductMapperSpec extends DatabaseTest {

    @Autowired
    ProductMapper productMapper
    @Autowired
    BrandMapper brandMapper
    @Autowired
    SizeMapper sizeMapper
    @Autowired
    ProductSizeMapper productSizeMapper

    def "save Product"() {
        given:
        def size = SizeFixtures.createUs8Size()
        def brand = BrandFixtures.createBrand()
        def sizes = new Sizes(Arrays.asList(size))
        def sneakers = createSneakers(brand, sizes)

        sizeMapper.addSize(size)
        brandMapper.addBrand(brand)

        expect:
        productMapper.addProduct(sneakers) == 1
    }

}
