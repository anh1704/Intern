
CREATE TABLE users (
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(100)        NOT NULL,
    email     VARCHAR(100) UNIQUE NOT NULL,
    phone     VARCHAR(20),
    tier      VARCHAR(20)         NOT NULL DEFAULT 'normal',
    city      VARCHAR(50),
    created_at TIMESTAMP          NOT NULL DEFAULT NOW()
);

CREATE TABLE products (
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(200)   NOT NULL,
    category   VARCHAR(50)    NOT NULL,
    price      DECIMAL(12, 2) NOT NULL,
    stock      INT            NOT NULL DEFAULT 0,
    created_at TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE TABLE orders (
    id           SERIAL PRIMARY KEY,
    user_id      INT            NOT NULL REFERENCES users (id),
    status       VARCHAR(20)    NOT NULL DEFAULT 'pending',
    total_amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
    note         TEXT,
    created_at   TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE TABLE order_items (
    id         SERIAL PRIMARY KEY,
    order_id   INT            NOT NULL REFERENCES orders (id),
    product_id INT            NOT NULL REFERENCES products (id),
    quantity   INT            NOT NULL,
    unit_price DECIMAL(12, 2) NOT NULL
);