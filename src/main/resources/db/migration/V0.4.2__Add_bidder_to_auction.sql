ALTER TABLE auction
    ADD COLUMN bidder_id bigint default null comment '낙찰자의 ID';
