package kr.flab.fream.domain.user.service;

import kr.flab.fream.domain.user.model.User;
import kr.flab.fream.mybatis.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 사용자 서비스.
 *
 * @since 1.0.0
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User getUser(Long id) {
        return userMapper.getUser(id);
    }


}
