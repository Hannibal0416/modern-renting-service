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

INSERT INTO vehicle(id, model_id, rent_price, name, color, production_year, seat_count, transmission, fuel_type) VALUES
('bce22263-6461-44de-9fd9-272c906fc15f', 1, 3000, 'Hannibal', 'White', 2022, 5, 'AT', '95'),
('8f569028-5b4a-45f6-977c-682bbe47aaea', 2, 2500, 'Nyotie', 'Black', 2016, 4, 'AT', '95'),
('ef9cd5a7-a80e-4a91-9ce7-2d0349da37a7', 3, 6000, 'Allen', 'Blue', 2020, 5, 'AT', '95'),
('d17eb127-8fee-4ebe-94ea-5c01dde9d26f', 4, 8000, 'Ricky', 'Yellow', 2018, 4, 'MT', '98');