--liquibase formatted sql
--changeset zaitis:17
CREATE TABLE order_log(
    id bigint NOT NULL auto_increment PRIMARY KEY,
    order_id bigint NOT NULL,
    created datetime NOT NULL,
    note text
);