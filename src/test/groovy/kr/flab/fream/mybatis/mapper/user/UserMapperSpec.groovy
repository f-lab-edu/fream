package kr.flab.fream.mybatis.mapper.user


import kr.flab.fream.domain.user.model.User
import kr.flab.fream.mybatis.mapper.product.ProductMapper
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import spock.lang.Specification

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperSpec extends Specification {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductMapper productMapper;

    def "update user"() {
        given:
        def user = new User();
        user.setId(1);
        user.setEmail("test@gmil.com");
        user.setAccount("test");
        user.setName("mapperTest");
        user.setPassword("ggggg")
        userMapper.updateUser(user);

        expect:
        userMapper.getUser(1).getPassword() == "ggggg"
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
        given:
        def user = new User();
        user.setId(1);

        expect:
        userMapper.getUser(1).getPassword()=="1234"
    }

    def "delete user"() {
        given:
        def user = new User();
        user.setId(1);

        expect:
        userMapper.deleteUser(user) == 1
    }
}