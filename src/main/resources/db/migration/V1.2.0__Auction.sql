CREATE TABLE IF NOT EXISTS auction
(
    id          BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(5)     NOT NULL COMMENT '입찰 타입 - 판매 입찰(ASK), 구매 입찰(BID), 완료된 입찰(SALES)',
    price       DECIMAL(19, 2) NOT NULL,
    product_id  BIGINT         NOT NULL,
    size_id     BIGINT         NOT NULL,
    user_id     BIGINT         NOT NULL,
    created_at  DATETIME       NOT NULL,
    due_date    DATETIME       NOT NULL COMMENT '입찰 마감 기한',
    canceled_at DATETIME COMMENT '입찰 취소한 시각',
    signed_at   DATETIME COMMENT '거래 체결된 시각'
);

