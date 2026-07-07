USE logistics;

CREATE TABLE drivers (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    phone      VARCHAR(20)  NOT NULL,
    license    VARCHAR(50)  NOT NULL,
    status     VARCHAR(20)  NOT NULL DEFAULT 'available',
    city       VARCHAR(50)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE shipments (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    order_id       INT            NOT NULL,
    driver_id      INT            REFERENCES drivers (id),
    origin         VARCHAR(200)   NOT NULL,
    destination    VARCHAR(200)   NOT NULL,
    status         VARCHAR(20)    NOT NULL DEFAULT 'pending',
    weight_kg      DECIMAL(8, 2),
    note           TEXT,
    created_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE driver_locations (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    driver_id   INT           NOT NULL REFERENCES drivers (id),
    lat         DECIMAL(10,7) NOT NULL,
    lng         DECIMAL(10,7) NOT NULL,
    speed_kmh   DECIMAL(5,1),
    recorded_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);