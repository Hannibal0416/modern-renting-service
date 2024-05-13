--liquibase formatted sql

--changeset application:1
CREATE TABLE IF NOT EXISTS authority (
    id serial NOT NULL PRIMARY KEY,
    name varchar(32) NOT NULL
);