package kr.flab.fream.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller 에서 인증된 유저의 정보를 받을 파라미터에 사용.
 * <pre>
 * Example.
 * {@code
 *     @RestController
 *     public class ExampleController {
 *
 *         @PostMapping
 *         public Something register(
 *             @Authentication User user,
 *             @RequestBody Something dto) {
 *             // Do something...
 *         }
 *     }
 * }
 * </pre>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authentication {

}
