package kr.flab.fream.domain.user.service

import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.controller.user.LoginDto
import kr.flab.fream.controller.user.UserDto
import kr.flab.fream.domain.user.model.User
import kr.flab.fream.mybatis.mapper.user.UserMapper
import kr.flab.fream.util.EncryptHelper
import org.modelmapper.ModelMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpSession
import org.springframework.web.server.ResponseStatusException
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

        EncryptHelper encryptHelper = Stub(){
            encryptPassword(_ as String) >> { String plainPassword -> org.mindrot.jbcrypt.BCrypt.hashpw(plainPassword)}
        }

        def userService = new UserService(userMapper, modelMapper, encryptHelper)
        expect:
        userService.getUserById(1L).getId()==1L;
    }

    def"user login"(){
        given :
        def modelMapper = new ModelMapperConfiguration().modelMapper();

        UserMapper userMapper = Stub() {
            getUser(_ as LoginDto) >> { LoginDto LoginInfo -> new User(LoginInfo.getEmail(),LoginInfo.getPassword())}
        }
        EncryptHelper encryptHelper = Stub(){
            encryptPassword(_ as String) >> { String plainPassword -> org.mindrot.jbcrypt.BCrypt.hashpw(plainPassword)}
        }

        LoginDto LoginInfo = new LoginDto("test@test.com","1234");
        def userService = new UserService(userMapper, modelMapper,encryptHelper)

        expect:
        userService.userLogin(LoginInfo).getEmail()==LoginInfo.getEmail();
    }

    def"user login failed"(){
        given :
        def modelMapper = new ModelMapperConfiguration().modelMapper();

        UserMapper userMapper = Stub() {
            getUser(_ as LoginDto) >> { null}
        }

        EncryptHelper encryptHelper = Stub(){
            encryptPassword(_ as String) >> { String plainPassword -> org.mindrot.jbcrypt.BCrypt.hashpw(plainPassword)}
        }

        LoginDto loginInfo = new LoginDto("test@test.com","1234");
        def userService = new UserService(userMapper, modelMapper, encryptHelper)

        when:
        userService.userLogin(loginInfo)

        then:
        thrown(ResponseStatusException)

    }

    def "joinUser"(){
        given:
        def user = new User();
        def modelMapper = new ModelMapperConfiguration().modelMapper();

        EncryptHelper encryptHelper = Stub(){
            encryptPassword(_ as String) >> { String plainPassword -> org.mindrot.jbcrypt.BCrypt.hashpw(plainPassword)}
        }

        UserMapper userMapper = Stub() {
            joinUser(_ as User) >> { User userInfo -> 1}
        }

        def userService = new UserService(userMapper, modelMapper, encryptHelper)

        expect:
        1 == userService.signUpMember(user);
    }
}
