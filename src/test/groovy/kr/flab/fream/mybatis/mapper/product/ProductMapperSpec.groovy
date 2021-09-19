package kr.flab.fream.mybatis.mapper.product

import kr.flab.domain.product.BrandFixtures
import kr.flab.domain.product.ProductFixtures
import kr.flab.domain.product.SizeFixtures
import kr.flab.fream.DatabaseTest
import kr.flab.fream.domain.product.Keyword
import kr.flab.fream.domain.product.model.Sizes
import kr.flab.fream.mybatis.util.ExtendedRowBounds
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

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
        def sneakers = ProductFixtures.createSneakers(brand, sizes)

        sizeMapper.addSize(size)
        brandMapper.addBrand(brand)

        expect:
        productMapper.addProduct(sneakers) == 1
    }

    def "get Product by ID"() {
        given:
        def size = SizeFixtures.createUs8Size()
        def size2 = SizeFixtures.createUs85Size()
        def brand = BrandFixtures.createBrand()
        def sizes = new Sizes(Arrays.asList(size, size2))
        def sneakers = ProductFixtures.createSneakers(brand, sizes)

        sizeMapper.addSize(size)
        sizeMapper.addSize(size2)
        brandMapper.addBrand(brand)
        productMapper.addProduct(sneakers)
        productSizeMapper.mapSizesToProduct(sneakers)

        when:
        def actual = productMapper.getProductById(sneakers.id)

        then:
        def config = new RecursiveComparisonConfiguration()
        config.ignoreCollectionOrder(true)
        assertThat(actual).usingRecursiveComparison(config).isEqualTo(sneakers)
    }

    // 아래 테스트 케이스들은 seed data 를 사용함. resources/db/migration-seed 데이터 확인

    def "search Nike products"() {
        given:
        def actual = productMapper.search(Keyword.of(str))

        expect:
        actual.size() == 6

        where:
        str << ['나이키', 'nike']
    }

    def "search Off-White products"() {
        given:
        def actual = productMapper.search(Keyword.of(str))

        expect:
        actual.size() == 2

        where:
        str << ['오프화이트', 'off-white']
    }

    def "search NB 992 product"() {
        given:
        def actual = productMapper.search(Keyword.of("992"))

        expect:
        actual.size() == 1
    }

    def "get Products with Pagination"() {
        given:
        def rowBounds = ExtendedRowBounds.of(0, 10)

        when:
        def products = productMapper.getProducts(rowBounds)

        then:
        products.size() == 10
    }
}
