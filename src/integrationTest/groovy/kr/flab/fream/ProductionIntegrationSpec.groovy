package kr.flab.fream

import io.restassured.http.ContentType
import kr.flab.domain.product.BrandFixtures
import kr.flab.domain.product.SizeFixtures
import kr.flab.fream.domain.product.OrderOption
import kr.flab.fream.domain.product.model.Category
import kr.flab.fream.domain.product.model.Product
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus

import java.util.function.Predicate
import java.util.stream.Stream

import static io.restassured.RestAssured.given
import static java.util.stream.Collectors.toList
import static kr.flab.domain.product.ProductFixtures.*
import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document

class ProductionIntegrationSpec extends BaseIntegrationSpec {

    static final def PRODUCTS_URL = "/products"

    @LocalServerPort
    int port

    private static final def SEARCH_API_REQUEST_PARAMS = requestParameters(
        parameterWithName("keyword")
            .description("입력한 키워드로 검색할 때 사용. 키워드를 사용할 때는 반드시 두 글자 이상 입력해야 한다.")
            .optional(),
        parameterWithName("page")
            .description("1 이상 값을 입력해야 하며, 입력하지 않으면 1이 기본 값이다.")
            .optional(),
        parameterWithName("category")
            .description("카테고리")
            .optional(),
        parameterWithName("brandIdList")
            .description("브랜드 ID 리스트")
            .optional(),
        parameterWithName("sizeIdList")
            .description("사이즈 ID 리스트")
            .optional(),
        parameterWithName("orderOption")
            .description("정렬 옵션 - 인기순과 발매일순 두 가지 옵션 제공하며, 생략 시 인기순" +
                "적용")
            .optional()
    )

    def "get products"() {
        given:
        def request = given(this.spec).accept(ContentType.JSON).params(params)
            .filter(document(identifier, SEARCH_API_REQUEST_PARAMS))
            .log()
            .all()

        when:
        def response = request.when().port(this.port).get(PRODUCTS_URL)

        then:
        List<String> actual = response.jsonPath().getList('name')

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expect.collect(toList()))

        where:
        identifier << [
            "products/search", "products/search/paging",
            "products/search/keyword", "products/search/category"
        ]
        params << [
            Collections.emptyMap(),
            Map.of("page", "2"),
            Map.of("keyword", "나이키"),
            Map.of("category", "BOTTOM"),
        ]
        expect << [
            sortByViewCount(allProducts().stream()).limit(10).map(p -> p.name),
            sortByViewCount(allProducts().stream()).skip(10).limit(10).map(p -> p.name),
            sortByViewCount(getNikeProducts().stream()).map(p -> p.name),
            sortByViewCount(allProducts().stream()).filter(p -> p.category == Category.BOTTOM)
                .map(p -> p.name),
        ]
    }

    def "get products have brands"() {
        given:
        def nikeAndSupreme =
            Arrays.asList(BrandFixtures.getNike(), BrandFixtures.getSUPREME())
                .stream()
                .map(b -> b.id)
                .collect(toList())
        def params = Map.of("brandIdList", nikeAndSupreme)
        def request = given(this.spec).accept(ContentType.JSON).params(params)
            .filter(document("products/search/brands", SEARCH_API_REQUEST_PARAMS))
            .log()
            .all()

        when:
        def response = request.when().port(this.port).get(PRODUCTS_URL)

        then:
        def expect = sortByViewCount(allProducts().stream())
            .filter(p -> nikeAndSupreme.contains(p.getBrand().id))
            .map(p -> p.name)
            .collect(toList())
        List<String> actual = response.jsonPath().getList('name')

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expect)
    }


    def "get products have sizes"() {
        given:
        def smallClothingSizes = SizeFixtures.getClothingSizes().sizeList.stream()
            .limit(2)
            .map(s -> s.id)
            .collect(toList())
        def params = Map.of("sizeIdList", smallClothingSizes)
        def request = given(this.spec).accept(ContentType.JSON).params(params)
            .filter(document("products/search/sizes", SEARCH_API_REQUEST_PARAMS))
            .log()
            .all()

        when:
        def response = request.when().port(this.port).get(PRODUCTS_URL)

        then:
        Predicate<Product> hasSomeClothingSizes = p -> p.sizes.sizeList.stream().map(s -> s.id)
            .collect(toList())
            .containsAll(smallClothingSizes)
        def expect = sortByViewCount(allProducts().stream())
            .filter(hasSomeClothingSizes)
            .map(p -> p.name)
            .collect(toList())
        List<String> actual = response.jsonPath().getList('name')

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expect)
    }

    def "get products using the complex search option"() {
        given:
        def someBrands = Arrays.asList(BrandFixtures.getNike(), BrandFixtures.getSUPREME())
            .stream()
            .map(b -> b.id)
            .collect(toList())
        def someClothingSizes = SizeFixtures.getClothingSizes().sizeList.stream()
            .limit(2)
            .map(s -> s.id)
            .collect(toList())
        def params =
            Map.of("sizeIdList", someClothingSizes,
                "brandIdList", someBrands,
                "keyword", "pants",
                "category", "BOTTOM"
            )
        def request = given(this.spec).accept(ContentType.JSON).params(params)
            .filter(document("products/search/complex", SEARCH_API_REQUEST_PARAMS))
            .log()
            .all()

        when:
        def response = request.when().port(this.port).get(PRODUCTS_URL)

        then:
        def expect = sortByViewCount(
            Arrays.asList(getNikeOffWhiteNrgPantsBlack(), getNikeStussyBeachPantsOffNoir(),
                getSupremeMeshPocketBeltedCargoPantsBlack()).stream())
            .map(p -> p.name)
            .collect(toList())
        List<String> actual = response.getBody().jsonPath().getList('name')

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expect)
    }

    def "sort by #orderOption"() {
        given:
        def request = given(this.spec).accept(ContentType.JSON)
            .params("orderOption", orderOption.name())
            .filter(document("products/search/sort", SEARCH_API_REQUEST_PARAMS))
            .log()
            .all()

        when:
        def response = request.when().port(this.port).get(PRODUCTS_URL)

        then:
        List<String> actual = response.jsonPath().getList('name')

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expect.collect(toList()))

        where:
        orderOption << [
            OrderOption.POPULAR, OrderOption.RECENTLY_RELEASED
        ]
        expect << [
            sortByViewCount(allProducts().stream()).limit(10).map(p -> p.name),
            sortByReleaseDate(allProducts().stream()).limit(10).map(p -> p.name),
        ]
    }

    def "give 4xx errors when client uses wrong search options"() {
        given:
        def request = given(this.spec).accept(ContentType.JSON).params(params)
            .filter(document(identifier, SEARCH_API_REQUEST_PARAMS))
            .log()
            .all()

        when:
        def response = request.when().port(this.port).get(PRODUCTS_URL)

        then:
        response.then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())

        where:
        identifier                        || params
        "products/search/invalidPaging"   || Map.of("page", "0")
        "products/search/invalidKeyword"  || Map.of("keyword", "")
        "products/search/invalidCategory" || Map.of("category", "NONE")
    }

    private static Stream sortByViewCount(Stream<Product> s) {
        return s.sorted((a, b) -> -1 * (a.viewCount <=> b.viewCount))
    }

    private static Stream sortByReleaseDate(Stream<Product> s) {
        return s.sorted((a, b) -> {
            def releaseDate1 = a.details.releaseDate
            def releaseDate2 = b.details.releaseDate

            if (Objects.equals(releaseDate1, releaseDate2)) {
                return 0
            }
            if (releaseDate1 == null) {
                return 1
            }
            if (releaseDate2 == null) {
                return -1
            }
            return -1 * (releaseDate1 <=> releaseDate2)
        })
    }

}
