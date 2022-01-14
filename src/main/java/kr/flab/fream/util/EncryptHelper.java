package kr.flab.fream.util;

import org.springframework.stereotype.Component;

/**
 * 암복호화를 도와주는 추상클래스  사용하게될 라이브러리, 사용방식등이 달라 질수 있기에 일단 추상클래스로 선언함.
 */
public abstract class EncryptHelper {

    public abstract String encryptPassword(String password);

    public abstract Boolean comparePassword(String plainPassword, String encPassword);
}
