--liquibase formatted sql
--changeset Sergey Kovalev:3

INSERT INTO passports(passport_series, passport_number)
VALUES
    ('HB', '1234567'),
    ('HB', '1234568'),
    ('HB', '6532165'),
    ('MP', '7823564'),
    ('MP', '5474466'),
    ('MP', '8973552'),
    ('MP', '5444566'),
    ('MP', '8976543'),
    ('MK', '5465984'),
    ('MK', '2467899');