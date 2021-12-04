package kr.flab.fream.domain.user.service


import kr.flab.fream.domain.user.model.User
import kr.flab.fream.mybatis.mapper.user.UserMapper
import org.modelmapper.ModelMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class UserServiceSpec extends Specification {

    @SpringBean
    UserMapper userMapper = Stub() {
        getUser(_ as Long) >> { Long id -> new User(id) }
    }

    @Autowired
    UserService userService;

    def "get user"() {
        expect:
        userService.getUser(1L).getId()==1
    }
}
