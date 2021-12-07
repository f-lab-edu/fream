package kr.flab.fream.auth;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 인증을 처리할 인터셉터 인터페이스. 해당 인터페이스를 구현한 클래스는 반드시 빈으로 만들어
 * {@link kr.flab.fream.config.WebConfig} 에서 사용할 수 있게 해야 한다.
 */
public interface AuthenticationInterceptor extends HandlerInterceptor {

}
