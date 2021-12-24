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
import org.springframework.mock.web.MockHttpSession
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


    def "login success"() {
        given:
        userService.userLogin(_ as UserDto) >> { UserDto userDto -> new UserDto(userDto.getEmail(),userDto.getPassword())}
        def requestBody = objectMapper.writeValueAsString(new UserDto("test@test.com","1234"))

        expect: "login success"
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentType == "application/json"
    }
    def "login failed"() {
        given:
        userService.userLogin(_ as UserDto) >> { null }
        def requestBody = objectMapper.writeValueAsString(new UserDto("test@test.com","12334"))
        expect: "login failed"
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(thrown(Exception.class))

    }

    def "logout success"() {
        given:
        def userInfo = new UserDto("test@test.com","1234");
        def session = new MockHttpSession();
        session.setAttribute("userInfo",userInfo);

        expect: "logout success"
        mockMvc.perform(post("/user/logout"))
                .andExpect(status().isOk())
                .andReturn()
                .response
    }

    def "logout failed"() {
        given:
        def session = new MockHttpSession();

        expect: "logout success"
        mockMvc.perform(post("/user/logout"))
                .andExpect(status().isOk())
                .andReturn()
                .response
    }
}