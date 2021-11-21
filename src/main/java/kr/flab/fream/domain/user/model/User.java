package kr.flab.fream.domain.user.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자 클래스
 * 사용자들은 고유한 userId와 하나이상의 주소({@link Address})를 가진 주소록({@link AddressBook})으로 구성된다.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    long id;
    String password;
    String name;
    List<Address> addressBook;
    String email;
    String phone;
    String account;

    public User(Long id){
        this.id = id;
    }
}