package kr.flab.fream.config;

import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotNull;
import kr.flab.fream.auth.AbstractAuthenticationResolver;
import kr.flab.fream.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 인터셉터와 MethodArgumentResolver 등록.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    //private final AbstractAuthenticationResolver authenticationResolver;
    private final LoginInterceptor loginInterceptor;
    private static final List<String> URL_PATTERNS = Arrays.asList("/user/**");  //인터셉터가 동작 해야 될 요청 주소
    /*
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationResolver);
    }
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
            .addPathPatterns(URL_PATTERNS)
            .excludePathPatterns("/user/login/**");
    }

}
