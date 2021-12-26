package kr.flab.fream.domain.user.service;

import javax.servlet.http.HttpSession;
import kr.flab.fream.controller.user.LoginDto;
import kr.flab.fream.controller.user.UserDto;
import kr.flab.fream.domain.user.model.User;
import kr.flab.fream.mybatis.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 사용자 서비스.
 *
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserMapper userMapper;
    private final ModelMapper modelMapper;

    public UserDto getUserById(Long id) {
        return modelMapper.map(userMapper.getUserById(id), UserDto.class);
    }

    /**
     *
     * @param loginInfo '로그인 정보'
     * @return
     */

    public UserDto userLogin(LoginDto loginInfo) {
        if (!ObjectUtils.isEmpty(userMapper.getUser(loginInfo))) {
            return modelMapper.map(userMapper.getUser(loginInfo), UserDto.class);
        }
        return null;
    }
}
