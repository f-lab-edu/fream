package kr.flab.fream.controller;

import java.time.LocalDateTime;
import java.util.List;
import kr.flab.fream.mybatis.util.exception.NoAuthenticationException;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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
     * 
     * MethodArgumentNotValid오류 핸들링을 위한 메소드
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        List<FieldError> errList = ex.getBindingResult().getFieldErrors();
        StringBuffer errMessage = new StringBuffer();

        for(FieldError err : errList){
            errMessage.append(err.getDefaultMessage())
                .append(" ");
        }

        final var errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .error(errMessage.toString())
            .build();
        return handleExceptionInternal(ex,errorResponse,headers,status,request);
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
