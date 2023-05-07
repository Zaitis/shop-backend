--liquibase formatted sql
--changeset zaitis:14
ALTER TABLE order_row MODIFY product_id bigint;
ALTER TABLE order_row ADD shipment_id bigint;
ALTER TABLE order_row ADD CONSTRAINT fk_order_row_shipment_id FOREIGN KEY (shipment_id) REFERENCES shipment(id);