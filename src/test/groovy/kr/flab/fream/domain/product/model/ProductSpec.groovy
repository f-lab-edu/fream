package kr.flab.fream.domain.product.model

import spock.lang.Specification

class ProductSpec extends Specification {

    def "get the size with the size ID"() {
        given:
        def targetSizes = new Sizes()
        def id = 1L
        def sizeString = "260"
        def size = new Size(id, sizeString)
        targetSizes.sizeList.add(size)
        Product product = new Product()
        product.sizes = targetSizes

        expect:
        product.getSize(1L) == new Size(id, sizeString)
    }

    def "throw IllegalArgumentException if product does not have that size"() {
        given:
        def product = new Product()
        product.sizes = new Sizes()

        when:
        product.getSize(1L)

        then:
        thrown IllegalArgumentException
    }

}
