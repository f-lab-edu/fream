package kr.flab.fream.mybatis.util.exception;

/**
 * 인증 정보가 존재하지 않을 때 던지는 예외.
 *
 * @author Jake
 * @since 1.0.0
 */
public class NoAuthenticationException extends RuntimeException {

    public NoAuthenticationException() {
        super("인증 정보가 필요합니다.");
    }
}
