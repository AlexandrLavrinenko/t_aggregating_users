--liquibase formatted sql

--changeset lao:2
CREATE TABLE IF NOT EXISTS aggregating_users2_repository.public.users
(
    user_id    UUID PRIMARY KEY,
    login      TEXT UNIQUE,
    first_name TEXT,
    last_name  TEXT
);
--rollback DROP TABLE aggregating_users2_repository.public.users;
