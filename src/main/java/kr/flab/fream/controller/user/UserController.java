package kr.flab.fream.controller.user;

import java.net.http.HttpRequest;
import java.util.List;
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

    @RequestMapping(value = "/login/{email}/{password}")
    public UserDto login(HttpSession session,
        @NotNull  @PathVariable String email
        ,@NotNull @PathVariable String password){

        if(session.isNew()){
            return (UserDto)session.getAttribute("userInfo");
        }

        UserDto userDto = new UserDto(email,password);
        UserDto userInfo = userService.userLogin(userDto);
        if(!ObjectUtils.isEmpty(userInfo)){
            session.setAttribute("userInfo",userInfo);
        }
        return userInfo;
    }

}