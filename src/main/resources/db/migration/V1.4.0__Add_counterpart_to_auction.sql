ALTER TABLE auction
    ADD COLUMN counterparty_id bigint default null comment '낙찰자의 ID';
