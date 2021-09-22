package kr.flab.fream

import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import org.junit.Rule
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.restdocs.JUnitRestDocumentation

import static io.restassured.RestAssured.expect
import static io.restassured.RestAssured.given
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document

class ProductionIntegrationSpec extends BaseIntegrationSpec {

    static final def PRODUCTS_URL = "/products"

    @LocalServerPort
    int port;

    def "get products and result will be '#status'"() {
        given:
        def request = given(this.spec).accept(ContentType.JSON).params(params)
            .filter(document("products"))
            .log()
            .all()

        when:
        def response = request.when().port(this.port).get(PRODUCTS_URL)

        then:
        response.then().log().all()
            .statusCode(status.value())
            .spec(specification)

        where:
        params                                 || status                 || specification
        new HashMap<>()                        || HttpStatus.OK          || expect()
        Map.of("page", "1")                    || HttpStatus.OK          || expect()
        Map.of("keyword", "nike")              || HttpStatus.OK          || expect()
        Map.of("page", "1", "keyword", "nike") || HttpStatus.OK          || expect()
        Map.of("page", "0")                    || HttpStatus.BAD_REQUEST || expect()
        Map.of("keyword", "")                  || HttpStatus.BAD_REQUEST || expect()
    }
    
}
