package kr.flab.fream.auth;

import java.time.LocalDateTime;
import java.util.Collections;
import javax.annotation.Nonnull;
import kr.flab.fream.domain.user.model.User;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 임시로 사용할 {@link AbstractAuthenticationResolver} 의 구현체.
 */
@Component
public class TemporaryAuthenticationResolver extends AbstractAuthenticationResolver {

    @Override
    public Object resolveArgument(
            @Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer,
            @Nonnull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return new User(1L, "password", "user", Collections.emptyList(), "", "", null,
                LocalDateTime.now(), null);
    }
}
