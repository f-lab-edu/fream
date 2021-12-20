package kr.flab.fream.controller;

import java.time.LocalDateTime;
import kr.flab.fream.mybatis.util.exception.NoAuthenticationException;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * 애플리케이션 전반에서 발생하는 예외를 처리.
 *
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 유저 입력이 잘못된 경우 여기에서 처리한다.
     *
     * @param e 발생한 예외
     * @return 발생한 예외에 대한 리포트 반환
     */
    @ExceptionHandler(value
            = {IllegalArgumentException.class})
    protected ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException e) {
        final var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAuthenticationException.class)
    protected ResponseEntity<ErrorResponse> handleNoAuthentication(RuntimeException e) {
        final var httpStatus = HttpStatus.UNAUTHORIZED;

        final var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .error(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * 글로벌 예외가 발생할 때 사용할 DTO 객체.
     *
     * @since 1.0.0
     */
    @Value
    @Builder
    public static class ErrorResponse {

        LocalDateTime timestamp;
        Integer status;
        String error;
    }

}
