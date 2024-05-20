--liquibase formatted sql

--changeset application:1 runAlways:true
INSERT INTO vehicle_type (id, name, image_uri) VALUES
(100001, 'SUV', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3e/97-01_Jeep_Cherokee.jpg/440px-97-01_Jeep_Cherokee.jpg'),
(100002, 'Sedan', 'https://en.wikipedia.org/wiki/File:1928_Model_A_Ford.jpg');

INSERT INTO vehicle_brand (id, name, image_uri) VALUES
(100001, 'Honda', 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/76/Honda_logo.svg/220px-Honda_logo.svg.png'),
(100002, 'Porsche', 'https://upload.wikimedia.org/wikipedia/en/thumb/8/8c/Porsche_logo.svg/200px-Porsche_logo.svg.png'),
(100003, 'Toyota', 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Toyota.svg/220px-Toyota.svg.png');

INSERT INTO vehicle_model(id, type_id, brand_id, name, image_uri) VALUES
(100001, 100001, 100001, 'CR-V', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a1/2023_Honda_CR-V_EX_AWD_in_Lunar_Silver_Metallic%2C_front_left.jpg/560px-2023_Honda_CR-V_EX_AWD_in_Lunar_Silver_Metallic%2C_front_left.jpg'),
(100002, 100002, 100003, 'Camry', 'https://en.wikipedia.org/wiki/File:2018_Toyota_Camry_(ASV70R)_Ascent_sedan_(2018-08-27)_01.jpg'),
(100003, 100001, 100002, 'Cayenne', 'https://en.wikipedia.org/wiki/File:2019_Porsche_Cayenne_V6_Tiptronic_3.0_Front.jpg'),
(100004, 100002, 100002, '911', 'https://en.wikipedia.org/wiki/File:Porsche_911_No_1000000,_70_Years_Porsche_Sports_Car,_Berlin_(1X7A3888).jpg');

INSERT INTO vehicle(id, model_id, rent_price, name, color, production_year, seat_count, transmission, fuel_type) VALUES
('bce22263-6461-44de-9fd9-272c906fc15f', 100001, 3000, 'Hannibal', 'White', 2022, 5, 'AT', '95'),
('8f569028-5b4a-45f6-977c-682bbe47aaea', 100002, 2500, 'Nyotie', 'Black', 2016, 4, 'AT', '95'),
('ef9cd5a7-a80e-4a91-9ce7-2d0349da37a7', 100003, 6000, 'Allen', 'Blue', 2020, 5, 'AT', '95'),
('d17eb127-8fee-4ebe-94ea-5c01dde9d26f', 100004, 8000, 'Ricky', 'Yellow', 2018, 4, 'MT', '98');

--changeset application:2 context:docker runAlways:true
SELECT SETVAL(pg_get_serial_sequence('vehicle_brand', 'id'), (SELECT MAX(id) FROM vehicle_brand));
SELECT SETVAL(pg_get_serial_sequence('vehicle_model', 'id'), (SELECT MAX(id) FROM vehicle_model));
SELECT SETVAL(pg_get_serial_sequence('vehicle_type', 'id'), (SELECT MAX(id) FROM vehicle_type));