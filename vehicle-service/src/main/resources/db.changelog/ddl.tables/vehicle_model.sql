--liquibase formatted sql

--changeset application:1
CREATE TABLE IF NOT EXISTS vehicle_model (
    id SERIAL PRIMARY KEY,
    type_id INTEGER NOT NULL REFERENCES vehicle_type(id),
    brand_id INTEGER NOT NULL REFERENCES vehicle_brand(id),
    image_uri VARCHAR,
    name VARCHAR(64) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);