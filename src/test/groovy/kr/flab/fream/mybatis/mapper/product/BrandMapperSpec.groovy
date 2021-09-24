package kr.flab.fream.mybatis.mapper.product

import kr.flab.domain.product.BrandFixtures
import kr.flab.fream.DatabaseTest
import kr.flab.fream.domain.product.model.Brand
import kr.flab.fream.mybatis.mapper.product.BrandMapper
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandMapperSpec extends DatabaseTest {

    @Autowired
    BrandMapper brandMapper

    def "save a list of Brands"() {
        given:
        List<Brand> brands = Arrays.asList(
            new Brand(null, "brand1", "brand1"),
            new Brand(null, "brand2", "brand2"),
        )

        expect:
        brandMapper.addBrands(brands) == brands.size()
    }


    def "save Brand"() {
        given:
        def brand = BrandFixtures.createBrand()

        when:
        brandMapper.addBrand(brand)

        then:
        brand.getId() != null
    }
}
