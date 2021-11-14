package kr.flab.fream.domain.user.service;

import kr.flab.fream.domain.user.model.User;

/**
 * 사용자 서비스의 인터페이스.
 *
 * @since 1.0.0
 */
public interface UserService {

    User getUser(Long id);

}
