package kr.flab.fream.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import jdk.javadoc.internal.doclets.toolkit.util.InternalException
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
import org.springframework.test.web.servlet.MockMvcBuilder
import org.testcontainers.shaded.com.trilead.ssh2.Session
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

    @SpringBean
    UserService userService = Stub()


    def "login success"() {
        given:"loginInfo"
        def requestBody = objectMapper.writeValueAsString(new UserDto("test@test.com","1234"))

        when:"valid input"
        userService.userLogin(_ as UserDto) >> { UserDto userDto -> new UserDto(userDto.getEmail(),userDto.getPassword())}

        then: "login success"
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
    }
    def "login failed"() {
        given:"login info"
        def requestBody = objectMapper.writeValueAsString(new UserDto("test@test.com","12334"))

        when: "no valid input process"
        userService.userLogin(_ as UserDto) >> { null }

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        then: "invoke 'not a valid input' exceptino"
        thrown(Exception)
    }

    def "logout success"() {
        given:"session has userinfo"
        def userInfo = new UserDto("test@test.com","1234");
        def session = new MockHttpSession();
        session.setAttribute("userInfo",userInfo);

        when:"processing logout"
        mockMvc.perform(post("/user/logout").session(session))

        then:"logout success"
        session.isInvalid() == true
    }

    def "logout failed"() {
        given:"session has not attr userinfo"
        def session = new MockHttpSession();

        when:"processing logout"
        mockMvc.perform(post("/user/logout").session(session))

        then: "login filed with exception 'required userInfo' "
        thrown(Exception)
    }
}