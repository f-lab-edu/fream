package kr.flab.fream.domain.user.service;

import kr.flab.fream.domain.user.model.User;
import kr.flab.fream.mybatis.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 임시로 사용하는 사용자 서비스. 추후 완성된 구현체로 바꿔끼기로 한다.
 */
@Service
public class TemporaryUserService implements UserService {

    @Autowired
    UserMapper userMapper;

    public User getUser(Long id) {
        return userMapper.getUser(id);
        //return null;
    }


}
