package kr.flab.fream.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import kr.flab.fream.config.FormattingConfiguration
import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.domain.auction.service.AuctionService
import kr.flab.fream.domain.user.model.User
import kr.flab.fream.domain.user.service.UserService
import kr.flab.fream.mybatis.mapper.user.UserMapper
import org.modelmapper.ModelMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserController)
@Import([ObjectMapper, ModelMapperConfiguration, FormattingConfiguration, UserService])
class UserControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    ModelMapper modelMapper

    @SpringBean
    UserService userService = Stub()

    def "simple assertion test"() {
        //givenultActions.andExpect(status().is(HttpStatus.OK.value()))
        expect:
        1 == 1
    }

    def "when get is performed then the response has status 200 and content is 'Hello world!'"() {

        expect: "Status is 200 and the response is 'Hello world!'"
        mockMvc.perform(get("/user/hello"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString == "Hello world!"
    }

    def "login success through interceptor"() {
        given:
        userService.userLogin(_ as UserDto) >> { UserDto userDto -> new UserDto(userDto.getEmail(),userDto.getPassword())}

        expect: "Status is 200 and the response is 'userDto'"
        mockMvc.perform(post("/user/login/test@test.com/1234"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentType == "application/json"

    }
    def "login failed through interceptor"() {
        //given:

        expect: "Status is 200 and the response is 'null'"
        mockMvc.perform(post("/user/login/test@test.com/12343"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentType == null

    }
}