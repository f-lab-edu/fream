CREATE TABLE IF NOT EXISTS brand
(
    id           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(128) NOT NULL UNIQUE,
    english_name VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS size
(
    id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS product
(
    id           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(128) NOT NULL COMMENT '상품명',
    english_name VARCHAR(128) NOT NULL COMMENT '영문 상품명',
    product_code VARCHAR(64) COMMENT '브랜드에서 지정한 상품 코드',
    release_date DATE COMMENT '발매일',
    retail_price BIGINT COMMENT '발매가',
    brand_id     BIGINT       NOT NULL,
    category     VARCHAR(32)  NOT NULL,

    FOREIGN KEY (brand_id) REFERENCES brand (id)
);

CREATE TABLE IF NOT EXISTS product_size
(
    product_id BIGINT NOT NULL,
    size_id    BIGINT NOT NULL,

    PRIMARY KEY (product_id, size_id),

    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (size_id) REFERENCES size (id)
);

