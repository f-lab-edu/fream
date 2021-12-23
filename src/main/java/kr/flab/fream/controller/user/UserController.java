package kr.flab.fream.controller.user;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import kr.flab.fream.controller.product.ProductDto;
import kr.flab.fream.domain.product.OrderOption;
import kr.flab.fream.domain.product.OrderOption.OrderOptionConverter;
import kr.flab.fream.domain.product.SearchOption;
import kr.flab.fream.domain.product.model.Product;
import kr.flab.fream.domain.product.service.ProductService;
import kr.flab.fream.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Product 컨트롤러.
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    //private final ModelMapper modelMapper;
   //private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/login")
    public UserDto login(HttpSession session,
        @NotNull  @RequestBody UserDto loginInfo){
        UserDto userDto = new UserDto(loginInfo.getEmail(),loginInfo.getPassword());
        UserDto userInfo = userService.userLogin(userDto);
        if(!ObjectUtils.isEmpty(userInfo)){
            session.setAttribute("userInfo",userInfo);
        }
        return userInfo;
    }

    @RequestMapping(value = "/test")
    public UserDto test(HttpSession session,
        @NotNull  @RequestBody UserDto loginInfo){
        UserDto userDto = new UserDto(loginInfo.getEmail(),loginInfo.getPassword());
        //UserDto userInfo = userService.userLogin(userDto);
        /*if(!ObjectUtils.isEmpty(userInfo)){
            session.setAttribute("userInfo",userInfo);
        }*/
        return userDto;
    }

    /**
     * 사용자 로그아웃
     * @param session
     */
    @RequestMapping(value = "/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }


}

