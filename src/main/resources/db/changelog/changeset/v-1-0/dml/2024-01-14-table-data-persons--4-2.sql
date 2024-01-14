--liquibase formatted sql
--changeset Sergey Kovalev:6

INSERT INTO persons(uuid, name, surname, sex, passport_id, residence_id, create_date, update_date)
VALUES
    ('ae679fa9-0955-48f7-8276-c4492875f609', 'Igor', 'Ovsiannikov', 'MALE', 1, 1, '2018-08-29T06:12:15.156', '2018-08-29T06:12:15.156'),
    ('421776cb-284a-47cc-a236-a32b464361a7', 'Maria', 'Sennikova', 'FEMALE', 2, 1, '2018-08-29T06:12:15.156', '2018-08-29T06:12:15.156'),
    ('b3d6f2fc-4c0f-40f9-86f7-64638abaada4', 'Dmitriy', 'Ordzhanikidze', 'MALE', 3, 1, '2018-08-29T06:12:15.156', '2018-08-29T06:12:15.156'),
    ('3f155aed-b0f5-4fbb-bb6f-aa77d6be25e9', 'Oleg', 'Sevostianchik', 'MALE', 4, 2, '2020-01-16T10:00:00.123', '2020-01-16T10:00:00.123'),
    ('8617df91-3c84-4f00-bb03-634139c70297', 'Zhanna', 'Karpova', 'FEMALE', 5, 2, '2021-12-30T10:00:00.321', '2021-12-30T10:00:00.321'),
    ('39e8260d-6a85-4716-9e9b-b3e9c32b879f', 'Mikhail', 'Oreshkin', 'MALE', 6, 3, '2002-02-01T10:55:12.652', '2002-02-01T10:55:12.652'),
    ('08e43698-a843-4c42-a54b-cab829b9f1a8', 'Olga', 'Petrova', 'FEMALE', 7, 4, '2013-11-20T15:11:11.165', '2013-11-20T15:11:11.165'),
    ('6b5f2624-e77d-4e1d-911b-595695452007', 'Pavel', 'Kuzmenkov', 'MALE', 8, 4, '2020-05-16T15:10:16.465', '2020-05-16T15:10:16.465'),
    ('fd54080c-57a7-4a25-9deb-102a088e7a2f', 'Denis', 'Korobeinik', 'MALE', 9, 4, '2023-10-30T15:08:55.877', '2023-10-30T15:08:55.877'),
    ('c1e71b34-c883-4b6d-a51e-30c7b0a4c589', 'Albert', 'Einshtein', 'MALE', 10, 3, '2019-08-01T10:13:00.564', '2019-08-01T10:13:00.564');