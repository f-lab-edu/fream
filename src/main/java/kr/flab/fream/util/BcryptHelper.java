package kr.flab.fream.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * EncryptHelper의 구현체, JBcrypt라이브러리를 사용, 암호화기능을 구현.
 */
@Component
public class BcryptHelper extends EncryptHelper {

    /**
     * 사용자비밀번호를 받아서, Bcrypt 방식으로 단방향 해시암호화를 한다.
     *
     * @param password '사용자 비밀번호'
     * @return hashedPw 'Bcrypt로 암호화된 비밀번호'
     */
    @Override
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public Boolean comparePassword(String plainPassword, String encPassword) {
        return BCrypt.checkpw(plainPassword, encPassword);
    }
}
