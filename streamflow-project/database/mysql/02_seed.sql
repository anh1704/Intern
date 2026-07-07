USE logistics;

INSERT INTO drivers (name, phone, license, status, city) VALUES
('Trần Văn Long',   '0912222221', '51A-12345', 'available',   'Hồ Chí Minh'),
('Nguyễn Thị Mai',  '0912222222', '29B-67890', 'available',   'Hà Nội'),
('Lê Văn Nam',      '0912222223', '43C-11223', 'on_delivery', 'Đà Nẵng'),
('Phạm Văn Phúc',   '0912222224', '51A-44556', 'available',   'Hồ Chí Minh'),
('Hoàng Thị Quyên', '0912222225', '29B-77889', 'off',         'Hà Nội');

INSERT INTO shipments (order_id, driver_id, origin, destination, status, weight_kg) VALUES
(1, 1, 'Kho Q1, HCM',        '123 Nguyễn Huệ, Q1, HCM',      'delivered',  1.2),
(2, 2, 'Kho Cầu Giấy, HN',   '45 Trần Duy Hưng, HN',          'in_transit', 0.8),
(3, 3, 'Kho Hải Châu, ĐN',   '78 Bạch Đằng, ĐN',              'assigned',   0.5),
(4, NULL, 'Kho Q7, HCM',      '99 Nguyễn Thị Thập, Q7, HCM',   'pending',    2.1),
(5, NULL, 'Kho Q1, HCM',      '10 Đỗ Thị Kim, Q1, HCM',        'pending',    1.5);

INSERT INTO driver_locations (driver_id, lat, lng, speed_kmh) VALUES
(1, 10.7756, 106.7019, 25.5),
(2, 21.0285, 105.8048, 30.0),
(3, 16.0544, 108.2022, 0.0);