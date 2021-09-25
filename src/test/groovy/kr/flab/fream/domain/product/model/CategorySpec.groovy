package kr.flab.fream.domain.product.model

import spock.lang.Specification

import static kr.flab.fream.domain.product.model.Category.*

class CategorySpec extends Specification {

    def "get children(#children) of Category '#category'"() {
        expect:
        category.getAllChildren().containsAll(children)

        where:
        category    || children
        CLOTHING    || Set.of(OUTER, TOP, BOTTOM)
        ACCESSORIES || Set.of(HAT, BAG, WALLET, ACCESSORIES_ETC)
        TECH        || Set.of(GRAPHIC_CARD, GAMING_CONSOLE, TECH_ETC)
        SNEAKERS    || Set.of()
        LIFE        || Set.of()
    }
}
