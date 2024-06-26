--liquibase formatted sql

--changeset application:1

CREATE TABLE IF NOT EXISTS vehicle (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    model_id INTEGER NOT NULL,
    image_uri VARCHAR,
    rent_price BIGINT NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- metadata
    name VARCHAR(64),
    color VARCHAR(64),
    production_year SMALLINT,
    seat_count SMALLINT,
    transmission VARCHAR(64),
    fuel_type VARCHAR(64)
);