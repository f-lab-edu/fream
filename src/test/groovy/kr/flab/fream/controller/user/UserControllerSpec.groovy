package kr.flab.fream.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import jdk.javadoc.internal.doclets.toolkit.util.InternalException
import kr.flab.fream.config.FormattingConfiguration
import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.domain.auction.service.AuctionService
import kr.flab.fream.domain.user.model.User
import kr.flab.fream.domain.user.service.UserService
import kr.flab.fream.interceptor.LoginInterceptor
import kr.flab.fream.mybatis.mapper.user.UserMapper
import kr.flab.fream.mybatis.util.exception.NoAuthenticationException
import org.apache.tomcat.jni.Status
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.server.ResponseStatusException
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

    @Autowired
    UserController userController

    @Autowired
    LoginInterceptor loginInterceptor




    def "login success"() {
        given:"loginInfo"
        def requestBody = objectMapper.writeValueAsString(new LoginDto("test@test.com","1234"))

        when:"valid input"
        //userService.userLogin(_ as UserDto) >> { UserDto userDto -> new UserDto(userDto.getEmail(),userDto.getPassword())}
        userService.userLogin(_ as LoginDto) >> { LoginDto LoginInfo -> new LoginDto(LoginInfo.getEmail(),LoginInfo.getPassword())}

        then: "login success"
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
    }
    /**
     * @TODO:service 레벨에서 exception을 던지고 재작성요함
     * @return
     */
    def "login failed"() {
        given:"login info"
        def requestBody = objectMapper.writeValueAsString(new LoginDto("test@test.com","12334"))
        def mvc = MockMvcBuilders.standaloneSetup(userController)
                .addInterceptors(loginInterceptor)
                .build()
        when: "no valid input process"
        //userService.userLogin(_ as UserDto) >> { null }
        userService.userLogin(_ as LoginDto) >> { null }

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        then: "invoke 'not a valid input' exceptino"
        thrown(NoAuthenticationException)
        //thrown(Exception)
        //1==1
    }
    /**
     * @TODO:service 레벨에서 exception을 던지고 재작성요함
     * @return
     */
    def "invalid loginInfo "() {
        given: "login info"
        def requestBody = objectMapper.writeValueAsString(new LoginDto("test@test.com","12334"))

        when: "no valid input process"
        userService.userLogin(_ as LoginDto) >> { throw ResponseStatusException}

        def resultAction = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        then: "invoke 'not a valid input' exceptino"
        resultAction.andExpect(status().isUnauthorized())
    }



    def "logout success"() {
        given:"session has userinfo"
        def userInfo = new UserDto();
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
        def mvc = MockMvcBuilders.standaloneSetup(userController)
                .addInterceptors(loginInterceptor)
                .build()
        when:"processing logout"
        def resultAction=mvc.perform(post("/user/logout").session(session))

        then: "login filed with exception 'required userInfo' "
        //resultAction.andExpect(status().isUnauthorized())
        //thrown(NoAuthenticationException.class)
        thrown(NoAuthenticationException)
    }
}