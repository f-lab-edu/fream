package kr.flab.fream.mybatis.mapper.user

import kr.flab.fream.DatabaseTest
import kr.flab.fream.domain.user.model.User
import kr.flab.fream.domain.user.service.UserService
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.mock.web.MockHttpSession

import javax.servlet.ServletContext
import javax.servlet.http.HttpSession
import javax.servlet.http.HttpSessionContext
import java.net.http.HttpRequest

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperSpec extends DatabaseTest {

    @Autowired
    AddressMapper addressMapper;
    @Autowired
    UserMapper userMapper;
    def "update user"() {
        given:
        def user = new User();
        user.setId(1L);
        user.setEmail("test@gmil.com");
        user.setAccount("test");
        user.setName("mapperTest");
        user.setPassword("ggggg")
        userMapper.updateUser(user);

        expect:
        userMapper.getUser(1L).getPassword() == "ggggg"
    }

    def "add user"() {
        given:
        def user = new User();
        user.setEmail("test@gmil.com");
        user.setAccount("test");
        user.setName("mapperTest");
        user.setPassword("ggggg");
        user.setPhone("1234");

        expect:
        userMapper.joinUser(user) == 1
    }
    def "get user"() {
        expect:
        userMapper.getUser(1L).getAddressBook().size()==3
    }

    def "delete user"() {
        given:
        def user = new User();
        user.setId(1L);
        user.setAddressBook(addressMapper.getAllAddress(user));
        addressMapper.deleteAddress(user.getAddressBook());
        expect:
        userMapper.deleteUser(user) == 1
    }

    def "login"(){
        given:
        def user = new User();
        //HttpRequest req = new HttpRequest();
        HttpSession session = new MockHttpSession();
        def userInfo = userMapper.getUserById(1L);
        session.setAttribute("userInfo",userInfo);

        expect:
        session.getAttribute("userInfo").equals(userInfo);
    }
}