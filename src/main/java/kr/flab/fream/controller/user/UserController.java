package kr.flab.fream.controller.user;

import java.util.List;
import javax.servlet.http.HttpSession;
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
    private final ModelMapper modelMapper;

    @RequestMapping(value = "/login")
    public Boolean login(HttpSession session){

        UserDto userDto = new UserDto("hpotter300@naver.com","12345678");
        Boolean resultCd = false;
        if(!ObjectUtils.isEmpty(userService.userLogin(userDto))){
            session.setAttribute("userInfo",userService.userLogin(userDto));
            resultCd = true;
        }
        return resultCd;
    }

}
