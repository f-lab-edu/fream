package kr.flab.fream.auth;

import javax.annotation.Nonnull;
import kr.flab.fream.controller.user.UserDto;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * {@link AbstractAuthenticationResolver} 의 구현체.
 */
@Component
public class AuthenticationResolver extends AbstractAuthenticationResolver {

    @Override
    public Object resolveArgument(
            @Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer,
            @Nonnull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return webRequest.getAttribute("userInfo", webRequest.SCOPE_SESSION);
    }
}
