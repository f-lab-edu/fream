package kr.flab.fream.domain.product

import kr.flab.fream.DatabaseTest
import kr.flab.fream.mybatis.mapper.product.BrandMapper
import kr.flab.fream.mybatis.mapper.product.ProductMapper
import kr.flab.fream.mybatis.mapper.product.ProductSizeMapper
import kr.flab.fream.mybatis.mapper.product.SizeMapper
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

import java.time.LocalDate
import java.time.Month

import static kr.flab.fream.domain.product.BrandMapperSpec.brand
import static kr.flab.fream.domain.product.SizeMapperSpec.us85Size
import static kr.flab.fream.domain.product.SizeMapperSpec.us8Size
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
        def size = us8Size()
        def nike = brand()
        def sizes = new Sizes(Arrays.asList(size))
        def sneakers = sneakers(nike, sizes)

        sizeMapper.addSize(size)
        brandMapper.addBrand(nike)

        expect:
        productMapper.addProduct(sneakers) == 1
    }

    def "get Product by ID"() {
        given:
        def size = us8Size()
        def size2 = us85Size()
        def nike = brand()
        def sizes = new Sizes(Arrays.asList(size, size2))
        def sneakers = sneakers(nike, sizes)

        sizeMapper.addSize(size)
        sizeMapper.addSize(size2)
        brandMapper.addBrand(nike)
        productMapper.addProduct(sneakers)
        productSizeMapper.mapSizesToProduct(sneakers)

        when:
        def actual = productMapper.getProductById(sneakers.id)

        then:
        def config = new RecursiveComparisonConfiguration()
        config.ignoreCollectionOrder(true)
        assertThat(actual).usingRecursiveComparison(config).isEqualTo(sneakers)
    }

    static def sneakers(Brand brand, Sizes sizes) {
        def details = new ProductDetails("ABCD-1234", LocalDate.of(2021, Month.AUGUST, 28), 239_000)
        return new Product(null, "신발", "sneakers", Category.SNEAKERS, details, brand, sizes)
    }
}
