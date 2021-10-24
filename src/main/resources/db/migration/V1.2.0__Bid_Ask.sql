CREATE TABLE IF NOT EXISTS bid
(
    id         BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    price      DECIMAL(19, 2) NOT NULL,
    product_id BIGINT         NOT NULL,
    size_id    BIGINT         NOT NULL,
    created_at DATETIME       NOT NULL,
    due_date   DATETIME       NOT NULL
);

CREATE TABLE IF NOT EXISTS ask
(
    id         BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    price      DECIMAL(19, 2) NOT NULL,
    product_id BIGINT         NOT NULL,
    size_id    BIGINT         NOT NULL,
    created_at DATETIME       NOT NULL,
    due_date   DATETIME       NOT NULL
);
