--liquibase formatted sql

--changeset application:1
INSERT INTO vehicle_type (id, name) VALUES
(10000, 'SUV'),
(20000, 'Sedan');

INSERT INTO vehicle_brand (id, name) VALUES
(10000, 'Honda'),
(20000, 'Porsche');

INSERT INTO vehicle_model(id, type_id, brand_id, name) VALUES
(10000, 10000, 10000, 'CR-V'),
(20000, 20000, 10000, 'Camry'),
(30000, 10000, 20000, 'Cayenne'),
(40000, 20000, 20000, '911');

INSERT INTO vehicle(id, model_id, rent_price, name, color, production_year, seat_count, transmission, fuel_type) VALUES
('bce22263-6461-44de-9fd9-272c906fc15f', 10000, 3000, 'Hannibal', 'White', 2022, 5, 'AT', '95'),
('8f569028-5b4a-45f6-977c-682bbe47aaea', 20000, 2500, 'Nyotie', 'Black', 2016, 4, 'AT', '95'),
('ef9cd5a7-a80e-4a91-9ce7-2d0349da37a7', 30000, 6000, 'Allen', 'Blue', 2020, 5, 'AT', '95'),
('d17eb127-8fee-4ebe-94ea-5c01dde9d26f', 40000, 8000, 'Ricky', 'Yellow', 2018, 4, 'MT', '98');

--changeset application:1 context:docker
SELECT SETVAL(pg_get_serial_sequence('vehicle_brand', 'id'), (SELECT MAX(id) FROM vehicle_brand));
SELECT SETVAL(pg_get_serial_sequence('vehicle_model', 'id'), (SELECT MAX(id) FROM vehicle_model));
SELECT SETVAL(pg_get_serial_sequence('vehicle_type', 'id'), (SELECT MAX(id) FROM vehicle_type));