--liquibase formatted sql
--changeset zaitis:7

INSERT INTO category (id, name, description, slug) VALUES (1, 'Others', '', 'others');
UPDATE product SET category_id=1;
ALTER TABLE product MODIFY category_id bigint NOT NULL;