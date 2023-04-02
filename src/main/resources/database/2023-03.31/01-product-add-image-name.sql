--liquibase formatted sql
--changeset zaitis:2
alter table product add image varchar(200) after currency;