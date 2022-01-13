package kr.flab.fream.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * {@code @Authentication} 을 사용할 때 인증한 유저를 컨트롤러에 주입해주는 resolver 의 추상 클래스.
 *
 * @since 1.0.0
 * @author Jake
 */
@Component
public abstract class AbstractAuthenticationResolver implements HandlerMethodArgumentResolver {

    @Override
    public final boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authentication.class);
    }

}
