package kr.flab.fream.controller.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import kr.flab.fream.domain.user.model.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 {@link kr.flab.fream.domain.user.model.User} 도메인 클래스의 DTO.
 *<P></P>
 * 사용자 정보를 노출할때 사용한다.
 *
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
public class UserDto implements Serializable {
    Long id;
    String password;
    String name;
    List<Address> addressBook;
    String email;
    String phone;
    String account;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    /**
     * 로그인시 이용되는 UserDto.
     * <P></P>
     *
     * @param email 사용자가 입력한 email
     * @param password 사용자가 입력한 password
     */
    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
