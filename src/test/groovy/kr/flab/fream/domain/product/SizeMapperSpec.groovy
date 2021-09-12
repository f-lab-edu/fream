package kr.flab.fream.domain.product

import kr.flab.fream.DatabaseTest
import kr.flab.fream.domain.product.model.Size
import kr.flab.fream.mybatis.mapper.product.SizeMapper
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SizeMapperSpec extends DatabaseTest {

    @Autowired
    SizeMapper sizeMapper

    def "save Size"() {
        given:
        def size = us8Size()

        expect:
        sizeMapper.addSize(size)
        size.id != null
    }

    def "save a list of Sizes"() {
        given:
        def size1 = us8Size()
        def size2 = us85Size()
        def sizes = Arrays.asList(size1, size2)

        expect:
        sizeMapper.addSizes(sizes) == 2
        size1.id != null
        size2.id != null
    }

    static def us8Size() {
        return new Size(null, "US 8.0")
    }

    static def us85Size() {
        return new Size(null, "US 8.5")
    }

}
