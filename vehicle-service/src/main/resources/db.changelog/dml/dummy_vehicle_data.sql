--liquibase formatted sql

--changeset application:1
INSERT INTO vehicle_type (id, name) VALUES
(1, 'SUV'),
(2, 'Sedan');
SELECT SETVAL(pg_get_serial_sequence('vehicle_type', 'id'), (SELECT MAX(id) FROM vehicle_type));

INSERT INTO vehicle_brand (id, name) VALUES
(1, 'Honda'),
(2, 'Porsche');
SELECT SETVAL(pg_get_serial_sequence('vehicle_brand', 'id'), (SELECT MAX(id) FROM vehicle_brand));

INSERT INTO vehicle_model(id, type_id, brand_id, name) VALUES
(1, 1, 1, 'CR-V'),
(2, 2, 1, 'Camry'),
(3, 1, 2, 'Cayenne'),
(4, 2, 2, '911');
SELECT SETVAL(pg_get_serial_sequence('vehicle_model', 'id'), (SELECT MAX(id) FROM vehicle_model));

INSERT INTO vehicle_model (model_id, rent_price, name, color, production_year, seat_count, transmission, fuel_type) VALUES
(1, 3000, 'Hannibal', 'White', 2022, 5, 'AT', '95'),
(2, 2500, 'Nyotie', 'Black', 2016, 4, 'AT', '95'),
(3, 6000, 'Allen', 'Blue', 2020, 5, 'AT', '95'),
(4, 8000, 'Ricky', 'Yellow', 2018, 4, 'MT', '98');