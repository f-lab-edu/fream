package kr.flab.fream.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import kr.flab.fream.config.FormattingConfiguration
import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.config.WebConfig
import kr.flab.fream.domain.user.model.User
import kr.flab.fream.domain.user.service.UserService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserController)
@Import([MappingJackson2HttpMessageConverter, ObjectMapper,ModelMapperConfiguration, FormattingConfiguration, UserService, WebConfig])
class UserControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    UserService userService = Stub()


    def "login success"() {
        given:"loginInfo"
        def requestBody = objectMapper.writeValueAsString(new LoginDto("test@test.com","1234"))

        when:
        userService.userLogin(_ as LoginDto) >> {LoginDto LoginInfo -> new UserDto()}

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
    def "invalid loginInfo "() {
        given: "login info"
        def requestBody = objectMapper.writeValueAsString(new LoginDto("test@test.com","12334"))

        when: "no valid input process"
        userService.userLogin(_ as LoginDto) >> { throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 정보가 유효하지 않습니다.")}
        def resultAction = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then: "invoke 'not a valid input'"
        resultAction.andExpect(status().isUnauthorized())
    }

    def "logout success"() {
        given:"session has userinfo"
        def userInfo = new UserDto();
        def session = new MockHttpSession();
        session.setAttribute("userInfo",userInfo);

        when:"processing logout"
        def rs = mockMvc.perform(post("/user/logout").session(session))

        then:"logout success"
        session.isInvalid() == true
        rs.andExpect(status().isOk())
    }

    /**
     * Interceptor 예외를 테스트가 받아주질 못함... ㅠ
     * @return
     */
    def "logout failed"() {
        given:"session has not attr userinfo"
        def session = new MockHttpSession();
        /*
        def mvc = MockMvcBuilders.standaloneSetup(userController)
                .addInterceptors(loginInterceptor)
                .build()
        */
        when:"processing logout"
        def resultAction=mockMvc.perform(post("/user/logout").session(session))

        then: "login filed with exception 'requir" +
                "ed userInfo' "
        resultAction.andExpect(status().isUnauthorized())
    }

    def"joinUser"(){
        given:"session has not attr userinfo"
        def user = new User();
        user.setEmail("test@gmil.com");
        user.setAccount("test");
        user.setName("mapperTest");
        user.setPassword("ggggg");
        user.setPhone("1234");

        def requestBody = objectMapper.writeValueAsString(user)

        when:"processing logout"
        def resultAction=mockMvc.perform(post("/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then: "login filed with exception 'requir" +
                "ed userInfo' "
        resultAction.andExpect(status().isOk())
    }
    def"failed joinUser"(){
        given:"session has not attr userinfo"
        def user = new User();
        user.setEmail("test@gmil.com");
        user.setAccount("test");

        def requestBody = objectMapper.writeValueAsString(user)

        when:"processing logout"
        def resultAction=mockMvc.perform(post("/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then: "login filed with exception 'requir" +
                "ed userInfo' "
        resultAction.andExpect(status().isUnauthorized())
    }
}