package kr.flab.fream

import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.http.ContentType
import kr.flab.fream.controller.user.LoginDto
import kr.flab.fream.controller.user.UserDto
import kr.flab.fream.domain.auction.model.AuctionType
import kr.flab.fream.domain.user.service.UserService
import kr.flab.fream.mybatis.mapper.auction.AuctionMapper
import kr.flab.fream.mybatis.mapper.product.ProductMapper
import kr.flab.fream.mybatis.mapper.user.AddressMapper
import kr.flab.fream.mybatis.mapper.user.UserMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.MockMvc

import javax.servlet.ServletContext
import javax.servlet.http.HttpSession
import javax.servlet.http.HttpSessionContext

import static io.restassured.RestAssured.given
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    MockMvc mockMvc;

    def setup(){
        given:"loginInfo"

        //def requestBody = objectMapper.writeValueAsString(new LoginDto("test@test.com","1234"))
        //def session = new MockHttpSession();
        def requestBody = (LoginDto)Map.of("email","test@test.com","password","1234")

        expect: "login success"
        session  << mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn().request.getSession()
    }

    def "create #type"() {
        given:
        def request = given(this.spec).contentType(ContentType.JSON).body(body)
            .filter(document(identifier))
            .log()
            .all()

        when:
        def response = request.when().sessionId(session.getId()).port(this.port).post("/" + identifier)


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

