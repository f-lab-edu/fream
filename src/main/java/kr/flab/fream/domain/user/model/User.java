package kr.flab.fream.domain.user.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자 클래스
 * 사용자들은 고유한 userId와 하나이상의 주소({@link Address})를 가진 주소록(List<Address></Address>)으로 구성된다.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String password;
    private String name;
    private List<Address> addressBook;
    private String email;
    private String phone;
    private String account;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(Long id) {
        this.id = id;
    }
}