--liquibase formatted sql
--changeset Sergey Kovalev:5

CREATE TABLE person_owners_houses (
    person_owner_id INTEGER NOT NULL,
    house_id INTEGER NOT NULL,
    PRIMARY KEY(person_owner_id, house_id),
    FOREIGN KEY(person_owner_id) REFERENCES persons(id),
    FOREIGN KEY(house_id) REFERENCES houses(id)
);