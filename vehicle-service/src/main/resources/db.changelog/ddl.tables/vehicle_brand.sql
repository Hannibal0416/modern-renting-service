--liquibase formatted sql

--changeset application:1
CREATE TABLE IF NOT EXISTS vehicle_brand (
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);