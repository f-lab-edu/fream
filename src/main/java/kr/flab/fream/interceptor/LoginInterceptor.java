package kr.flab.fream.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * loginInterceptor, 로그인한 사용자의 요청인지 확인한다.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler)throws Exception {
        if (ObjectUtils.isEmpty(request.getSession().getAttribute("userInfo"))) {
            throw new Exception("required userInfo");
        }
        return true;
    }
}
