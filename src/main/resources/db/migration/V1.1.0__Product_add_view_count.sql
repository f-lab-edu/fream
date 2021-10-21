ALTER TABLE product
    ADD COLUMN view_count BIGINT NOT NULL DEFAULT 0
        AFTER category,
    ALGORITHM = INPLACE,
    LOCK = NONE;

