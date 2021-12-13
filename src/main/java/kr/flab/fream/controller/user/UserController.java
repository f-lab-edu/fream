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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping(value = "/login")
    public UserDto login(HttpSession session){
        session.setAttribute("userInfo",userService.getUserById(1L));
        return (UserDto)session.getAttribute("userInfo");
    }

}
