package kr.flab.fream.controller.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 로그인 시 입력받을 값.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
public class LoginDto {
    @Email
    String email;
    @NotNull
    String password;
}
