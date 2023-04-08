--liquibase formatted sql
--changeset zaitis:11
CREATE TABLE cart(
    id bigint NOT NULL auto_increment PRIMARY KEY,
    created datetime NOT NULL
);

CREATE TABLE cart_item(
    id bigint NOT NULL auto_increment PRIMARY KEY,
    product_id bigint NOT NULL,
    quantity INT,
    cart_id bigint NOT NULL,
    CONSTRAINT fk_cart_item_product_id FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_cart_item_cart_id FOREIGN KEY (cart_id) REFERENCES cart(id)
);