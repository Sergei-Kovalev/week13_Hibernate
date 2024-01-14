--liquibase formatted sql
--changeset Sergey Kovalev:1

INSERT INTO addresses(country, city, street, number)
VALUES
    ('Belarus', 'Minsk', 'Partizansky avenue', '27A'),
    ('Belarus', 'Minsk', 'Kuprianova street', '48'),
    ('Belarus', 'Soligorsk', '12 Microregion', '16'),
    ('Belarus', 'Bobruisk', 'Mashinostroiteley street', '122'),
    ('Belarus', 'Gomel', 'Lenina avenue', '21');