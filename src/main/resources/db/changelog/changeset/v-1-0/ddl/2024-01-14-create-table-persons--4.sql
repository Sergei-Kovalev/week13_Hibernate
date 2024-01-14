--liquibase formatted sql
--changeset Sergey Kovalev:4

CREATE TABLE persons (
    id SERIAL NOT NULL,
    uuid UUID NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    sex VARCHAR(10) NOT NULL,
    passport_id INTEGER NOT NULL,
    residence_id INTEGER NOT NULL,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(passport_id) REFERENCES passports(id) ON DELETE RESTRICT,
    FOREIGN KEY(residence_id) REFERENCES houses(id)
);