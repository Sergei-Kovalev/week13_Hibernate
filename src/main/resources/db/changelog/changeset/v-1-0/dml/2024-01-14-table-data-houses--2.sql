--liquibase formatted sql
--changeset Sergey Kovalev:2

INSERT INTO houses(uuid, area, address_id, create_date)
VALUES
    ('75445f31-2cd7-41e2-8fde-1bedaf1526d7', 80.55, 1, '2018-08-29T06:12:15.156'),
    ('4eb6af8c-48ae-416b-8dd8-98935e8367a5', 60.22, 2, '2020-01-16T10:00:00.123'),
    ('a1b64898-7b1e-401a-8a7e-fd50af1bafec', 120.10, 3, '2002-02-01T10:55:12.652'),
    ('26b4e3fa-aa6c-4a4a-81e1-53d0cf996990', 48.12, 4, '1988-03-20T15:10:11.165'),
    ('5586015b-7b91-4f2e-a2df-4c20f76912e5', 66.26, 5, '1999-01-14T09:00:56.723');