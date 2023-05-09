--liquibase formatted sql
--changeset zaitis:16
ALTER TABLE `order` ADD payment_id BIGINT;
UPDATE `order` set payment_id=1;
ALTER TABLE `order` MODIFY payment_id BIGINT NOT NULL;