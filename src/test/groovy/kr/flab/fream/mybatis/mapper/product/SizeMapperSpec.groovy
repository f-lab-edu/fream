package kr.flab.fream.mybatis.mapper.product

import kr.flab.domain.product.SizeFixtures
import kr.flab.fream.DatabaseTest
import kr.flab.fream.mybatis.mapper.product.SizeMapper
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import spock.lang.Specification

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SizeMapperSpec extends Specification {

    @Autowired
    SizeMapper sizeMapper

    def "save Size"() {
        given:
        def size = SizeFixtures.createUs8Size()

        expect:
        sizeMapper.addSize(size)
        size.id != null
    }

    def "save a list of Sizes"() {
        given:
        def size1 = SizeFixtures.createUs8Size()
        def size2 = SizeFixtures.createUs85Size()
        def sizes = Arrays.asList(size1, size2)

        expect:
        sizeMapper.addSizes(sizes) == 2
        size1.id != null
        size2.id != null
    }

}
