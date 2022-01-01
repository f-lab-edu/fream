package kr.flab.fream.auth

import kr.flab.fream.config.WebConfig
import kr.flab.fream.controller.user.UserDto
import kr.flab.fream.domain.user.model.Address
import kr.flab.fream.domain.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import spock.lang.Specification

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TestController)
@Import(value = [WebConfig])
class AuthenticationResolverTest extends Specification {

    private static long userId = 1L

    @Autowired
    MockMvc mockMvc

    def "resolve an authentication"() {
        given:
        def session = new MockHttpSession();
        def userDto = new UserDto(1l,"1234","tester",
                Collections.emptyList(),"test@test.com","12345678",
                "12345678",LocalDateTime.now(),null)
        session.setAttribute("userInfo",userDto)

        def resultActions = mockMvc.perform(post("/authTest").session(session))

        expect:
        resultActions.andExpect(status().is(HttpStatus.OK.value()))

        and:
        resultActions.andExpect(jsonPath('$.userId').value(userId))

    }

    @RestController
    @RequestMapping("/authTest")
    private static class TestController {

        @PostMapping
        TestDto post(@Authentication UserDto user) {
            return new TestDto(user.getId())
        }

    }

    private static class TestDto {

        long userId

        TestDto(long userId) {
            this.userId = userId
        }

    }


}
