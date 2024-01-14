--liquibase formatted sql
--changeset Sergey Kovalev:3

CREATE TABLE passports (
    id SERIAL NOT NULL,
    passport_series VARCHAR(2) NOT NULL,
    passport_number VARCHAR(7) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE(passport_series, passport_number)
);