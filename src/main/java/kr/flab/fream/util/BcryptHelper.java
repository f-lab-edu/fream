package kr.flab.fream.util;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptHelper implements EncryptHelper{

    @Override
    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }
}
