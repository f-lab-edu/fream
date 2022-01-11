package kr.flab.fream.domain.user.service;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import kr.flab.fream.controller.user.LoginDto;
import kr.flab.fream.controller.user.UserDto;
import kr.flab.fream.domain.user.model.User;
import kr.flab.fream.mybatis.mapper.user.UserMapper;
import kr.flab.fream.util.BcryptHelper;
import kr.flab.fream.util.EncryptHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

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
    private final EncryptHelper encryptHelper;

    public UserDto getUserById(Long id) {
        return modelMapper.map(userMapper.getUserById(id), UserDto.class);
    }

    /**
     * 사용자 로그인.
     *
     * @param loginInfo '로그인 정보'
     * @return UserDto '로그인 한 유저 정보'
     * @throws ResponseStatusException '401, 로그인 정보가 유효하지 않음, 로그인에서만 쓰여서 GlobalException을 타지 않는다.'
     */

    public UserDto userLogin(LoginDto loginInfo) {
        if (!ObjectUtils.isEmpty(userMapper.getUser(loginInfo))) {
            return modelMapper.map(userMapper.getUser(loginInfo), UserDto.class);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 정보가 유효하지 않습니다.");
    }

    /**
     * 사용자가 회원가입을 한다.
     *
     * @param userInfo '회원정보'
     * @return '1 if success'
     */
    public int signUpMember(User userInfo) {
        userInfo.setPassword(encryptHelper.encryptPassword(userInfo.getPassword()));
        return userMapper.joinUser(userInfo);
    }
}
