package kr.flab.fream.mybatis.util

import spock.lang.Specification

class ExtendedRowBoundsSpec extends Specification {

    def "throw Exception if the number of page is less then 0"() {
        when:
        ExtendedRowBounds.of(0)

        then:
        thrown(IllegalArgumentException)
    }
}
