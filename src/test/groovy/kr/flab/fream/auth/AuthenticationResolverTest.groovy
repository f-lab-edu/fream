package kr.flab.fream.auth

import kr.flab.fream.config.WebConfig
import kr.flab.fream.domain.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import spock.lang.Specification

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
        def resultActions = mockMvc.perform(post("/authTest"))

        expect:
        resultActions.andExpect(status().is(HttpStatus.OK.value()))

        and:
        resultActions.andExpect(jsonPath('$.userId').value(userId))

    }

    @RestController
    @RequestMapping("/authTest")
    private static class TestController {

        @PostMapping
        TestDto post(@Authentication User user) {
            return new TestDto(user.getId())
        }

    }

    @Component
    @Primary
    private static class TestAuthenticationResolver extends AbstractAuthenticationResolver {

        @Override
        Object resolveArgument(MethodParameter parameter,
                               @Nullable ModelAndViewContainer mavContainer,
                               NativeWebRequest webRequest,
                               @Nullable WebDataBinderFactory binderFactory) throws Exception {
            User user = new User()
            user.id = userId
            return user
        }

    }

    private static class TestDto {

        long userId

        TestDto(long userId) {
            this.userId = userId
        }

    }


}
