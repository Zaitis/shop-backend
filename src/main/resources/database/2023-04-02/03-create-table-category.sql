--liquibase formatted sql
--changeset zaitis:5

CREATE TABLE category (
    id bigint NOT NULL auto_increment PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description text,
    slug VARCHAR(255) NOT NULL
    );