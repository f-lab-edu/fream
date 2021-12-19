package kr.flab.fream.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import kr.flab.fream.config.FormattingConfiguration
import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.domain.user.service.UserService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@WebMvcTest(UserController)
@Import([ObjectMapper, ModelMapperConfiguration, FormattingConfiguration])
class UserControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc;

    @SpringBean
    UserService userService;

    def "로그인 성공"() {
        given:
        def req = post("/user/test@test.com/1234");

        when:
        mockMvc.perform(req)
                .andExpect(status().isOk())

        then: "Status is 200 and the response is 'Hello world!'"
    }

    def "when get is performed then the response has status 200 and content is 'Hello world!'"() {
        expect: "Status is 200 and the response is 'Hello world!'"
        mockMvc.perform(post("/user/test@test.com/1234"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentType == UserDto

    }
}