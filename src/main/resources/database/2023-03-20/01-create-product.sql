--liquibase formatted sql
--changeset zaitis:1

create table product (
    id bigint not null auto_increment PRIMARY KEY,
    name varchar(100) not null,
    category varchar(100) not null,
    description text not null,
    price decimal(9,2) not null,
    currency varchar(4) not null
);