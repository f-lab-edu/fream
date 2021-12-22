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
import java.awt.Polygon

class UserServiceSpec extends Specification {
    def "simple assertion test"() {

        expect:
        1 == 0
    }

    def "should demonstrate g-w-t"() {
        given: //setting up conditions required for tests
        //def polygon = new Polygon(4);
        def tester = new int[4];

        when: // this is the thing  what actually testing the behavior we're trying to check
        int length = tester.size();

        then: //we will check all the conditions we expecting haven't met
        length == 4
    }

    def "should expect exceptions"(){
        when:
        def tester = new int[0];
        tester[2];
        then:
        thrown(IndexOutOfBoundsException.class)
    }

    def "should expect exceptions where"(){
        when:
        def tester = new int[len];
        tester[len+1]

        then:
        thrown(ArrayIndexOutOfBoundsException.class)

        where: //specify the input values for tests
         len << [-1,0,1,2]
    }


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
