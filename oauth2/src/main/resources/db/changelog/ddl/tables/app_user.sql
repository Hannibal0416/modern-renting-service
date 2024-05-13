CREATE TABLE IF NOT EXISTS app_user (
    id uuid NOT NULL PRIMARY KEY,
    username varchar(64) NOT NULL,
    password varchar(64) DEFAULT NULL,
    first_name varchar(32) DEFAULT NULL,
    middle_name varchar(32) DEFAULT NULL,
    last_name varchar(32) DEFAULT NULL,
    locale varchar(2) DEFAULT NULL,
    avatar_url varchar(2048) DEFAULT NULL,
    active boolean DEFAULT false NOT NULL,
    created_at timestamp without time zone NOT NULL
);