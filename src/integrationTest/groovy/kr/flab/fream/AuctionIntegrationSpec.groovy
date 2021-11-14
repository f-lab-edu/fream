package kr.flab.fream

import io.restassured.http.ContentType
import kr.flab.fream.domain.auction.model.AuctionType
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus

import static io.restassured.RestAssured.given
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document

class AuctionIntegrationSpec extends BaseIntegrationSpec {

    @LocalServerPort
    int port

    def "create #type"() {
        given:
        def request = given(this.spec).contentType(ContentType.JSON).body(body)
            .filter(document(identifier))
            .log()
            .all()

        when:
        def response = request.when().port(this.port).post("/" + identifier)

        then:
        response.getStatusCode() == HttpStatus.CREATED.value()

        where:
        identifier << [
            "auction/asks", "auction/bids",
        ]
        type << [AuctionType.ASK, AuctionType.BID]
        body << [
            Map.of("price", "100000", "productId", "1", "sizeId", "1", "userId", "1", "dueDays", "30", "type", "ASK"),
            Map.of("price", "100000", "productId", "1", "sizeId", "1", "userId", "1", "dueDays", "30", "type", "BID"),
        ]
    }

}
