--liquibase formatted sql

--changeset lao:1
CREATE TABLE IF NOT EXISTS aggregating_users1_repository.public.user
(
    id       TEXT UNIQUE,
    username TEXT UNIQUE,
    name     TEXT,
    surname  TEXT,
    PRIMARY KEY (id)
);
--rollback DROP TABLE aggregating_users1_repository.public.user;