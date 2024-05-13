--liquibase formatted sql

--changeset application:1
CREATE TABLE IF NOT EXISTS user_role (
    user_id uuid NOT NULL,
    role_id integer NOT NULL
);