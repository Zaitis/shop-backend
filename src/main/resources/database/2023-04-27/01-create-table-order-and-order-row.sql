--liquibase formatted sql
--changeset zaitis:12
CREATE TABLE `order`(
    id bigint NOT NULL auto_increment PRIMARY KEY,
    place_date datetime NOT NULL,
    order_status VARCHAR(32) NOT NULL,
    gross_value DECIMAL(6,2) NOT NULL,
    firstname VARCHAR(64) NOT NULL,
    lastname VARCHAR(64) NOT NULL,
    street VARCHAR(64) NOT NULL,
    zipcode VARCHAR(64) NOT NULL,
    city VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL,
    phone VARCHAR(64) NOT NULL

);
CREATE TABLE order_row(
    id bigint NOT NULL auto_increment PRIMARY KEY,
    order_id bigint NOT NULL,
    product_id bigint NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(6,2) NOT NULL,
    CONSTRAINT fk_order_row_order_id FOREIGN KEY (order_id) REFERENCES `order`(id),
    CONSTRAINT fk_order_row_product_id FOREIGN KEY (product_id) REFERENCES product(id)
);