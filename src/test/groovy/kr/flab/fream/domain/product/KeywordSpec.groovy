package kr.flab.fream.domain.product

import spock.lang.Specification

class KeywordSpec extends Specification {

    def "'Keyword' must be a string of at least two letters or null"() {
        when:
        Keyword.of(str)

        then:
        notThrown(IllegalArgumentException)

        where:
        str << ['as', 'of', 'nike', '레고', '아디다스', null]
    }

    def "Strings less than two letters or null cannot be 'Keyword'"() {
        when:
        Keyword.of(str)

        then:
        thrown(IllegalArgumentException)

        where:
        str << ['a', '가', '']
    }

}
