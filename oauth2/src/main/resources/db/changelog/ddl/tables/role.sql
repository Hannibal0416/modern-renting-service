--liquibase formatted sql

--changeset application:1
CREATE TABLE IF NOT EXISTS role (
    id serial NOT NULL PRIMARY KEY,
    name varchar(64) NOT NULL
);