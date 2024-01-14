--liquibase formatted sql
--changeset Sergey Kovalev:5

INSERT INTO person_owners_houses(person_owner_id, house_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (4, 3),
    (7, 4),
    (9, 5),
    (10, 2);