--liquibase formatted sql
--changeset Sergey Kovalev:1

CREATE TABLE addresses (
    id SERIAL NOT NULL,
    country VARCHAR(50) NOT NULL,
    city VARCHAR(100) NOT NULL,
    street VARCHAR(100) NOT NULL,
    number VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);