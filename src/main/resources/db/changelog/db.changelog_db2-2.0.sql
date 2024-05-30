--liquibase formatted sql

--changeset lao:2
CREATE TABLE IF NOT EXISTS aggregating_users2_repository.public.user
(
    id       TEXT UNIQUE,
    username TEXT UNIQUE,
    name     TEXT,
    surname  TEXT,
    PRIMARY KEY (id)
);
--rollback DROP TABLE aggregating_users2_repository.public.user;
