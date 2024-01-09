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
