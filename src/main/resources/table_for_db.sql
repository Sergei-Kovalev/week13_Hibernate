DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS houses;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS passports;

CREATE TABLE addresses (
    id SERIAL NOT NULL,
    country VARCHAR(50) NOT NULL,
    city VARCHAR(100) NOT NULL,
    street VARCHAR(100) NOT NULL,
    number VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE houses (
    id SERIAL NOT NULL,
    uuid UUID NOT NULL,
    area NUMERIC(10, 2) NOT NULL,
    address_id INTEGER NOT NULL,
    create_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(address_id) REFERENCES addresses(id) ON DELETE RESTRICT
);

CREATE TABLE passports (
    id SERIAL NOT NULL,
    passport_series VARCHAR(2) NOT NULL,
    passport_number VARCHAR(7) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE(passport_series, passport_number)
);

CREATE TABLE persons (
    id SERIAL NOT NULL,
    uuid UUID NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    sex VARCHAR(10) NOT NULL,
    passport_id INTEGER NOT NULL,
    house_owner_id INTEGER,
    residence_id INTEGER NOT NULL,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(passport_id) REFERENCES passports(id) ON DELETE RESTRICT,
    FOREIGN KEY(house_owner_id) REFERENCES houses(id),
    FOREIGN KEY(residence_id) REFERENCES houses(id)
);

INSERT INTO addresses(country, city, street, number)
VALUES
    ('Belarus', 'Minsk', 'Partizansky avenue', '27A'),
    ('Belarus', 'Minsk', 'Kuprianova street', '48'),
    ('Belarus', 'Soligorsk', '12 Microregion', '16'),
    ('Belarus', 'Bobruisk', 'Mashinostroiteley street', '122'),
    ('Belarus', 'Gomel', 'Lenina avenue', '21');

INSERT INTO houses(uuid, area, address_id, create_date)
VALUES
    ('75445f31-2cd7-41e2-8fde-1bedaf1526d7', 80.55, 1, '2018-08-29T06:12:15.156'),
    ('4eb6af8c-48ae-416b-8dd8-98935e8367a5', 60.22, 2, '2020-01-16T10:00:00.123'),
    ('a1b64898-7b1e-401a-8a7e-fd50af1bafec', 120.10, 3, '2002-02-01T10:55:12.652'),
    ('26b4e3fa-aa6c-4a4a-81e1-53d0cf996990', 48.12, 4, '1988-03-20T15:10:11.165'),
    ('5586015b-7b91-4f2e-a2df-4c20f76912e5', 66.26, 5, '1999-01-14T09:00:56.723');

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

INSERT INTO persons(uuid, name, surname, sex, passport_id, house_owner_id, residence_id, create_date, update_date)
VALUES
    ('ae679fa9-0955-48f7-8276-c4492875f609', 'Igor', 'Ovsiannikov', 'male', 1, 1, 1, '2018-08-29T06:12:15.156', '2018-08-29T06:12:15.156'),
    ('421776cb-284a-47cc-a236-a32b464361a7', 'Maria', 'Sennikova', 'female', 2, 1, 1, '2018-08-29T06:12:15.156', '2018-08-29T06:12:15.156'),
    ('b3d6f2fc-4c0f-40f9-86f7-64638abaada4', 'Dmitriy', 'Ordzhanikidze', 'male', 3, null, 1, '2018-08-29T06:12:15.156', '2018-08-29T06:12:15.156'),
    ('3f155aed-b0f5-4fbb-bb6f-aa77d6be25e9', 'Oleg', 'Sevostianchik', 'male', 4, 2, 2, '2020-01-16T10:00:00.123', '2020-01-16T10:00:00.123'),
    ('8617df91-3c84-4f00-bb03-634139c70297', 'Zhanna', 'Karpova', 'female', 5, null, 2, '2021-12-30T10:00:00.321', '2021-12-30T10:00:00.321'),
    ('39e8260d-6a85-4716-9e9b-b3e9c32b879f', 'Mikhail', 'Oreshkin', 'male', 6, 3, 3, '2002-02-01T10:55:12.652', '2002-02-01T10:55:12.652'),
    ('08e43698-a843-4c42-a54b-cab829b9f1a8', 'Olga', 'Petrova', 'female', 7, 4, 4, '2013-11-20T15:11:11.165', '2013-11-20T15:11:11.165'),
    ('6b5f2624-e77d-4e1d-911b-595695452007', 'Pavel', 'Kuzmenkov', 'male', 8, 5, 4, '2020-05-16T15:10:16.465', '2020-05-16T15:10:16.465'),
    ('fd54080c-57a7-4a25-9deb-102a088e7a2f', 'Denis', 'Korobeinik', 'male', 9, null, 4, '2023-10-30T15:08:55.877', '2023-10-30T15:08:55.877'),
    ('c1e71b34-c883-4b6d-a51e-30c7b0a4c589', 'Albert', 'Einshtein', 'male', 10, null, 3, '2019-08-01T10:13:00.564', '2019-08-01T10:13:00.564');