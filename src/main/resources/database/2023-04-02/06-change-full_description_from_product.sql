--liquibase formatted sql
--changeset zaitis:8

ALTER TABLE product MODIFY full_description text DEFAULT NULL;