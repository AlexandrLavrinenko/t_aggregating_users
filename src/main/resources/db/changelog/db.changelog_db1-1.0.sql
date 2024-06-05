--liquibase formatted sql

--changeset lao:1
CREATE TABLE IF NOT EXISTS aggregating_users1_repository.public.user
(
    id       uuid PRIMARY KEY,
    username TEXT UNIQUE,
    name     TEXT,
    surname  TEXT
);
--rollback DROP TABLE aggregating_users1_repository.public.user;