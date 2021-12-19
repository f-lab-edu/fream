package kr.flab.fream.domain.user.service

import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.controller.user.UserDto
import kr.flab.fream.domain.user.model.User
import kr.flab.fream.mybatis.mapper.user.UserMapper
import org.modelmapper.ModelMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpSession
import spock.lang.Specification

import javax.servlet.http.HttpSession

class UserServiceSpec extends Specification {
    def "get user By Id"() {
        given :
        def modelMapper = new ModelMapperConfiguration().modelMapper();

        UserMapper userMapper = Stub() {
            getUserById(_ as Long) >> { Long id -> new User(id) }
        }

        def userService = new UserService(userMapper, modelMapper)
        expect:
        userService.getUserById(1L).getId()==1L;
    }
    def"user login"(){
        given :
        def modelMapper = new ModelMapperConfiguration().modelMapper();

        UserMapper userMapper = Stub() {
            getUser(_ as UserDto) >> { UserDto userDto -> new User(userDto.getEmail(),userDto.getPassword())}
        }
        UserDto userDto = new UserDto("test@test.com","1234");
        def userService = new UserService(userMapper, modelMapper)

        expect:
        userService.userLogin(userDto).getPassword()=="1234"
    }
}
