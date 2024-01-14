--liquibase formatted sql
--changeset Sergey Kovalev:2

CREATE TABLE houses (
    id SERIAL NOT NULL,
    uuid UUID NOT NULL UNIQUE,
    area NUMERIC(10, 2) NOT NULL,
    address_id INTEGER NOT NULL,
    create_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(address_id) REFERENCES addresses(id) ON DELETE RESTRICT
);