--liquibase formatted sql
--changeset zaitis:9

CREATE TABLE review(
    id bigint NOT NULL auto_increment PRIMARY KEY,
    product_id bigint NOT NULL,
    author_name VARCHAR(60) NOT NULL,
    content text
);