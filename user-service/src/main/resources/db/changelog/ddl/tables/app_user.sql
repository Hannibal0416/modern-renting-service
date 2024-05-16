--liquibase formatted sql

--changeset application:1
CREATE TABLE IF NOT EXISTS app_user (
    id uuid NOT NULL PRIMARY KEY,
    username varchar(64) NOT NULL,
    password varchar(64) DEFAULT NULL,
    email varchar(64) DEFAULT NULL,
    phone varchar(64) DEFAULT NULL,
    avatar_url varchar(2048) DEFAULT NULL,
    active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS app_user_username_unique_idx ON app_user (username);