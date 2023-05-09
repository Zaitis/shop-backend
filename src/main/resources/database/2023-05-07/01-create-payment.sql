--liquibase formatted sql
--changeset zaitis:15
CREATE TABLE payment(
    id bigint NOT NULL auto_increment PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    type VARCHAR(32) NOT NULL,
    default_payment boolean DEFAULT FALSE,
    note text
);
INSERT INTO payment(id, name, type, default_payment, note)
VALUES (1, 'bank transfer', 'BANK_TRANSFER', true, 'please make payent at bank:\n12 3456 7890 09876 54321 1234 5678\n in the title please enter your order number');