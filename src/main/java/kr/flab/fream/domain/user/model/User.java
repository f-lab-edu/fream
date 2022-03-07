package kr.flab.fream.domain.user.model;

import java.time.LocalDateTime;
import java.util.List;
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
 * 사용자 클래스
 * 사용자들은 고유한 userId와 하나이상의 주소({@link Address})를 가진 주소록(List<Address></Address>)으로 구성된다.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    Long id;
    @NotNull
    String password;
    @NotNull
    String name;
    List<Address> addressBook;
    String phone;
    @NotNull
    String account;
    @Email
    @NotNull
    String email;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public User(Long id) {
        this.id = id;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}