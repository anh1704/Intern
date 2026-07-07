INSERT INTO users (name, email, phone, tier, city) VALUES
('Nguyễn Văn An',    'an.nguyen@mail.com',    '0901111111', 'vip',    'Hồ Chí Minh'),
('Trần Thị Bình',    'binh.tran@mail.com',    '0901111112', 'gold',   'Hà Nội'),
('Lê Văn Cường',     'cuong.le@mail.com',     '0901111113', 'normal', 'Đà Nẵng'),
('Phạm Thị Dung',    'dung.pham@mail.com',    '0901111114', 'silver', 'Hồ Chí Minh'),
('Hoàng Văn Em',     'em.hoang@mail.com',     '0901111115', 'normal', 'Cần Thơ'),
('Vũ Thị Phương',    'phuong.vu@mail.com',    '0901111116', 'gold',   'Hà Nội'),
('Đặng Văn Giang',   'giang.dang@mail.com',   '0901111117', 'normal', 'Hồ Chí Minh'),
('Bùi Thị Hoa',      'hoa.bui@mail.com',      '0901111118', 'vip',    'Đà Nẵng'),
('Ngô Văn Inh',      'inh.ngo@mail.com',      '0901111119', 'normal', 'Hải Phòng'),
('Đỗ Thị Kim',       'kim.do@mail.com',       '0901111120', 'silver', 'Hồ Chí Minh');

INSERT INTO products (name, category, price, stock) VALUES
('Áo thun cotton nam',        'clothing',    150000, 200),
('Quần jean slimfit',         'clothing',    350000, 120),
('Giày sneaker trắng',        'clothing',    650000, 80),
('Tai nghe Bluetooth',        'electronics', 450000, 150),
('Sạc dự phòng 20000mAh',     'electronics', 320000, 100),
('Chuột không dây',           'electronics', 180000, 200),
('Cà phê rang xay 500g',      'food',        95000,  300),
('Trà xanh Thái Nguyên',      'food',        65000,  250),
('Bánh quy hộp thiếc',        'food',        120000, 180),
('Ốp lưng điện thoại',        'electronics', 45000,  400);

INSERT INTO orders (user_id, status, total_amount) VALUES (1, 'delivered', 800000);
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
(1, 3, 1, 650000),
(1, 6, 1, 180000);

INSERT INTO orders (user_id, status, total_amount) VALUES (2, 'shipping', 350000);
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
(2, 2, 1, 350000);

INSERT INTO orders (user_id, status, total_amount) VALUES (3, 'confirmed', 280000);
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
(3, 7, 2, 95000),
(3, 8, 1, 65000),
(3, 10, 1, 45000);

INSERT INTO orders (user_id, status, total_amount) VALUES (8, 'pending', 770000);
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
(4, 4, 1, 450000),
(4, 5, 1, 320000);

INSERT INTO orders (user_id, status, total_amount) VALUES (10, 'pending', 500000);
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
(5, 1, 2, 150000),
(5, 2, 1, 350000);