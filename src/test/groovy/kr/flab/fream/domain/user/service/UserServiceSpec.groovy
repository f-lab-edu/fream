package kr.flab.fream.domain.user.service

import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.controller.user.LoginDto
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
import java.awt.Polygon

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
            getUser(_ as LoginDto) >> { LoginDto LoginInfo -> new User(LoginInfo.getEmail(),LoginInfo.getPassword())}
        }
        LoginDto LoginInfo = new LoginDto("test@test.com","1234");
        def userService = new UserService(userMapper, modelMapper)

        expect:
        userService.userLogin(LoginInfo).getPassword()=="1234"
    }

    def"user login failed"(){
        given :
        def modelMapper = new ModelMapperConfiguration().modelMapper();

        UserMapper userMapper = Stub() {
            getUser(_ as LoginDto) >> { null}
        }
        LoginDto loginInfo = new LoginDto("test@test.com","1234");
        def userService = new UserService(userMapper, modelMapper)

        expect:
        userService.userLogin(loginInfo)==null
    }
}
