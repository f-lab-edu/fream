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

    def "get Product by ID"() {
        given:
        def size = SizeFixtures.createUs8Size()
        def size2 = SizeFixtures.createUs85Size()
        def brand = BrandFixtures.createBrand()
        def sizes = new Sizes(Arrays.asList(size, size2))
        def sneakers = createSneakers(brand, sizes)

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

    def "search products by the keyword"() {
        given:
        def searchOption = SearchOption.builder().keyword(keyword).build()
        def actual = productMapper.search(searchOption)

        expect:
        actual.size() == expectElementSize

        where:
        keyword     || expectElementSize
        '나이키'       || 6
        '오프화이트'     || 2
        'off-white' || 2
        '992'       || 1
    }

    def "get Products with Pagination"() {
        given:
        def searchOption = SearchOption.builder()
            .page(1)
            .build()

        when:
        def products = productMapper.search(searchOption)

        then:
        products.size() == 10
    }

    def "get Products with Category"() {
        given:
        def searchOption = SearchOption.builder()
            .categoriesOf(Category.CLOTHING.name())
            .build()
        def expect = getClothes()

        expect:
        def actual = productMapper.search(searchOption)
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expect)
    }

    def "get Products By brands"() {
        given:
        def brandIds = Arrays.asList(BrandFixtures.getNike(), BrandFixtures.getADIDAS())
            .stream()
            .map(brand -> brand.getId())
            .collect(Collectors.toList())

        def searchOption = SearchOption.builder()
            .brandIdList(brandIds)
            .build()
        def expect =
            Stream.concat(getNikeProducts().stream(),
                getAdidasProducts().stream()).collect(Collectors.toList())

        expect:
        def actual = productMapper.search(searchOption)
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expect)
    }

    def "get Products that have sizes of Clothing"() {
        given:
        def sizeIds = SizeFixtures.getClothingSizes()
            .sizeList
            .stream()
            .map(size -> size.getId())
            .collect(Collectors.toList())

        def searchOption = SearchOption.builder()
            .sizeIdList(sizeIds)
            .build()
        def expect = getClothes()

        expect:
        def actual = productMapper.search(searchOption)
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expect)
    }

    def "get Products by the complex search option"() {
        given:
        def sizeIds = SizeFixtures.getClothingSizes()
            .sizeList
            .stream()
            .map(size -> size.getId())
            .collect(Collectors.toList())

        def brandIds = Arrays.asList(BrandFixtures.getNike().getId(),
            BrandFixtures.getSUPREME().getId())

        def keyword = "팬츠"

        def searchOption = SearchOption.builder()
            .keyword(keyword)
            .categoriesOf(Category.CLOTHING.name())
            .sizeIdList(sizeIds)
            .brandIdList(brandIds)
            .build()
        def expect = Arrays.asList(
            getNikeStussyBeachPantsOffNoir(),
            getNikeOffWhiteNrgPantsBlack(),
            getSupremeMeshPocketBeltedCargoPantsBlack()
        )

        expect:
        def actual = productMapper.search(searchOption)
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expect)
    }

    def "sort by View Counts"() {
        given:
        def searchOption = SearchOption.builder()
            .orderOption(OrderOption.POPULAR)
            .build()
        def expect = sortByViewCount(allProducts().stream())
            .limit(10)
            .collect(Collectors.toList())

        expect:
        def actual = productMapper.search(searchOption)
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expect)
    }

    def "sort by release date"() {
        given:
        def searchOption = SearchOption.builder()
            .orderOption(OrderOption.RECENTLY_RELEASED)
            .build()
        def expect = sortByReleaseDate(allProducts().stream())
            .limit(10)
            .collect(Collectors.toList())

        expect:
        def actual = productMapper.search(searchOption)
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expect)
    }

}
