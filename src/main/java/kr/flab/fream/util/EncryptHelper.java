package kr.flab.fream.util;

import org.springframework.stereotype.Component;


public interface EncryptHelper {
    String encryptPassword(String password);
}
