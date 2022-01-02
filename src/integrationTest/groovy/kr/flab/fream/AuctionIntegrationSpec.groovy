package kr.flab.fream

import io.restassured.http.ContentType
import kr.flab.fream.domain.auction.model.AuctionType
import kr.flab.fream.mybatis.mapper.auction.AuctionMapper
import kr.flab.fream.mybatis.mapper.product.ProductMapper
import kr.flab.fream.mybatis.mapper.user.AddressMapper
import kr.flab.fream.mybatis.mapper.user.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpSession

import static io.restassured.RestAssured.given
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document

class AuctionIntegrationSpec extends BaseIntegrationSpec {

    @LocalServerPort
    int port

    @Autowired
    ProductMapper productMapper

    @Autowired
    UserMapper userMapper

    @Autowired
    AuctionMapper auctionMapper

    @Autowired
    AddressMapper addressMapper

    def "create #type"() {
        given:
       def session = new MockHttpSession();
       session.setAttribute("userInfo","test")
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

    def "update an auction - type: #type"() {
        given:
        def reqBody = Map.of("price", "200000", "dueDays", "1")

        def request = given(this.spec).contentType(ContentType.JSON).body(reqBody)
            .filter(document("auction/" + type + "s/id"))
            .log()
            .all()


        when:
        def response = request.when().port(this.port).patch("/auction/asks/" + id)

        then:
        response.getStatusCode() == HttpStatus.OK.value()

        where:
        type << [
            "ask", "bid",
        ]
        id << [1, 2]
    }

    def "cancel an auction"() {
        given:
        def request = given(this.spec)
            .filter(document("auction/asks"))
            .log()
            .all()

        when:
        def response = request.when().port(this.port).delete("/auction/asks/1")

        then:
        response.getStatusCode() == HttpStatus.NO_CONTENT.value()
    }

    def "get auction summaries"() {
        given:
        def request = given(this.spec)
            .filter(document("auction/" + type + "/summaries"))
            .log()
            .all()

        when:
        def response = request.when().port(this.port).get("/auction/" + type + "/summaries?productId=1")

        then:
        response.getStatusCode() == HttpStatus.OK.value()

        where:
        type << ['asks', 'bids']
    }

}

