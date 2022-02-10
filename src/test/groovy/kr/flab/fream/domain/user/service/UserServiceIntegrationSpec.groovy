package kr.flab.fream.domain.user.service

import kr.flab.domain.product.BrandFixtures
import kr.flab.domain.product.SizeFixtures
import kr.flab.fream.IntegrationSpec
import kr.flab.fream.config.ModelMapperConfiguration
import kr.flab.fream.controller.user.LoginDto
import kr.flab.fream.domain.product.OrderOption
import kr.flab.fream.domain.product.SearchOption
import kr.flab.fream.domain.product.model.Category
import kr.flab.fream.domain.product.model.Sizes
import kr.flab.fream.domain.product.service.ProductService
import kr.flab.fream.domain.user.model.User
import kr.flab.fream.mybatis.mapper.product.BrandMapper
import kr.flab.fream.mybatis.mapper.product.ProductMapper
import kr.flab.fream.mybatis.mapper.product.ProductSizeMapper
import kr.flab.fream.mybatis.mapper.product.SizeMapper
import kr.flab.fream.mybatis.mapper.user.UserMapper
import kr.flab.fream.util.EncryptHelper
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

import java.util.stream.Collectors
import java.util.stream.Stream

import static kr.flab.domain.product.ProductFixtures.*
import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.assertThatNullPointerException

class UserServiceIntegrationSpec extends IntegrationSpec {

    @Autowired
    UserMapper userMapper

    @Autowired
    UserService userService

    def"user login without address"(){
        given :
        User joinInfo = new User("no@adress.co.kr","1234");
        joinInfo.setName('noAddressUser');
        joinInfo.setAccount("0000");
        joinInfo.setPhone("0000");

        LoginDto loginInfo = new LoginDto(joinInfo.getEmail(),joinInfo.getPassword());
        userService.signUpMember(joinInfo)

        when:
        def loginUser = userService.userLogin(loginInfo)

        then:
        notThrown(ResponseStatusException.class)




    }
}
