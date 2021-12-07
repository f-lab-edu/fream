package kr.flab.fream.config;

import java.util.List;
import kr.flab.fream.auth.AbstractAuthenticationResolver;
import kr.flab.fream.auth.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 인터셉터와 MethodArgumentResolver 등록.
 */
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final AbstractAuthenticationResolver authenticationResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationResolver);
    }

}
