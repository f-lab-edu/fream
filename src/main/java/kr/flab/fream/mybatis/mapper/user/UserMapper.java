package kr.flab.fream.mybatis.mapper.user;

import kr.flab.fream.controller.user.LoginDto;
import kr.flab.fream.controller.user.UserDto;
import kr.flab.fream.domain.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * {@link User} 클래스의 매퍼. 추후 구현된 다른 매퍼로 교체해야 한다.
 *
 * @since 1.0.0
 */
@Mapper
public interface UserMapper {

    User getUserById(Long id);

    User getUser(LoginDto loginInfo);

    int joinUser(User user);

    int updateUser(User user);

    int deleteUser(User user);

}
