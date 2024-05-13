--liquibase formatted sql

--changeset application:1
CREATE TABLE IF NOT EXISTS role_authority (
    role_id integer NOT NULL,
    authority_id integer NOT NULL
);