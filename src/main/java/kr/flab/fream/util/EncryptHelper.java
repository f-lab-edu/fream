package kr.flab.fream.util;

import org.springframework.stereotype.Component;

/**
 * 암복호화를 도와주는 인터페이스 라이브러리, 사용방식등이 달라 질수 있기에 인터페이스로 선언함.
 */
public interface EncryptHelper {
    String encryptPassword(String password);
}
