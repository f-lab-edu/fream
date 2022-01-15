CREATE TABLE IF NOT EXISTS auction_lock_by_product_id_and_size_id
(
    product_id  BIGINT         NOT NULL,
    size_id     BIGINT         NOT NULL,

    constraint auction_lock_by_product_id_and_size_id_pk
        primary key (product_id, size_id)
);
