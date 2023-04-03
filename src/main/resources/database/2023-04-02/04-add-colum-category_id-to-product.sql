--liquibase formatted sql
--changeset zaitis:6

ALTER TABLE product ADD category_id bigint after category;
ALTER TABLE product DROP COLUMN category;
ALTER TABLE product ADD CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES category(id);