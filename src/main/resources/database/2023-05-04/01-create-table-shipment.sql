--liquibase formatted sql
--changeset zaitis:13
CREATE TABLE shipment(
    id bigint NOT NULL auto_increment PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    price DECIMAL(6,2) NOT NULL,
    type VARCHAR(32) NOT NULL,
    default_shipment boolean DEFAULT FALSE
);
INSERT INTO  shipment(name, price, type, default_shipment) VALUES ('Deliveryman', 14.99, 'DELIVERYMAN', TRUE);
INSERT INTO shipment(name, price, type, default_shipment) VALUES ('Self pickup', 0.0, 'SELFPICKUP', FALSE);