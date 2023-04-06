--liquibase formatted sql
--changeset zaitis:10

ALTER TABLE review ADD moderated boolean DEFAULT FALSE;