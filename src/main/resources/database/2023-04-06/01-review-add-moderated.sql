--liquibase formatted sql
--changeset zaitis:9

ALTER TABLE review ADD moderated boolean DEFAULT FALSE;