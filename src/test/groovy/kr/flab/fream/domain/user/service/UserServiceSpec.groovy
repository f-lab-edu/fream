package kr.flab.fream.domain.user.service

import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.domain.user.model.User
import kr.flab.fream.mybatis.mapper.user.UserMapper
import org.modelmapper.ModelMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

class UserServiceSpec extends Specification {



    def "get user"() {
        given :
        def modelMapper = new ModelMapperConfiguration().modelMapper();

        UserMapper userMapper = Stub() {
            getUser(_ as Long) >> { Long id -> new User(id) }
        }

        def userService = new UserService(userMapper, modelMapper)
        expect:
        userService.getUser(1L).getId()==1
    }
}
