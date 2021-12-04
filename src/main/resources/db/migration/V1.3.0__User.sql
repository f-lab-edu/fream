CREATE TABLE IF NOT EXISTS `user`
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 ID',
    password VARCHAR(200) NOT NULL COMMENT '비밀번호(암호화)',
    name VARCHAR(128) NOT NULL COMMENT '사용자 이름',
    email VARCHAR(128) NOT NULL COMMENT '사용자 email',
    phone VARCHAR(128) COMMENT '사용자 전화번호',
    account VARCHAR(128) NOT NULL COMMENT '사용자 계좌',
    created_at DATETIME NOT NULL DEFAULT NOW() COMMENT '가입일자',
    updated_at DATETIME COMMENT '수정일자',
    UNIQUE KEY (email)
    );

CREATE TABLE IF NOT EXISTS address
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT NOT NULL COMMENT '사용자 ID',
    alias VARCHAR(128) COMMENT '별명',
    address VARCHAR(128) NOT NULL COMMENT '주소',
    address_detail VARCHAR(128) NOT NULL COMMENT '상세주소',
    is_default TINYINT NOT NULL COMMENT '기본 배송지 여부',
    created_at DATETIME NOT NULL DEFAULT NOW() COMMENT '생성일자',
    updated_at DATETIME COMMENT '수정일자',
    FOREIGN KEY (user_id) REFERENCES `user`(id)
    );