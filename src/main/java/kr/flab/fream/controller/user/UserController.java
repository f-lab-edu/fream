package kr.flab.fream.controller.user;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import kr.flab.fream.domain.user.model.User;
import kr.flab.fream.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User 컨트롤러.
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     *
     * <p></p>
     * 사용자 로그인.
     *
     * @param session "사용자 세션"
     * @param loginInfo "사용자 로그인 정보"
     * @return loginInfo "성공시 로그인 정보 반환"
     * @throws Exception "not a valid input"
     */
    @RequestMapping(value = "/login")
    public UserDto login(HttpSession session,
            @Valid @NotNull @RequestBody LoginDto loginInfo) throws Exception {
        UserDto userInfo = userService.userLogin(loginInfo);
        session.setAttribute("userInfo", userInfo);
        return userInfo;
    }

    /**
     *
     * <p></p>
     * 사용자 로그아웃.
     *
     * @param session 사용자 session
     */
    @RequestMapping(value = "/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }


    @RequestMapping(value = "join")
    public int joinUser(@Valid @NotNull @RequestBody User userInfo) {
        return userService.signUpMember(userInfo);
    }
}


