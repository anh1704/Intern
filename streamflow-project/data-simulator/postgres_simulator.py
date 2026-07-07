import psycopg2
import random
import time

conn = psycopg2.connect(
    host="localhost",
    port=5432,
    dbname="ecommerce",
    user="admin",
    password="anh0174"
)

conn.autocommit = True
cur = conn.cursor()

cur.execute("SELECT id FROM users")
user_ids = [row[0] for row in cur.fetchall()]

cur.execute("SELECT id, price FROM products")
products = cur.fetchall()


def create_random_order():
    user_id = random.choice(user_ids)

    num_items = random.randint(1, 3)
    chosen_products = random.sample(products, num_items)

    total_amount = 0
    items = []

    for product_id, price in chosen_products:
        qty = random.randint(1, 3)
        subtotal = float(price) * qty

        total_amount += subtotal
        items.append((product_id, qty, price))

    cur.execute(
        """
        INSERT INTO orders (user_id, status, total_amount)
        VALUES (%s, %s, %s)
        RETURNING id
        """,
        (user_id, "pending", total_amount)
    )

    order_id = cur.fetchone()[0]

    for product_id, qty, unit_price in items:
        cur.execute(
            """
            INSERT INTO order_items
                (order_id, product_id, quantity, unit_price)
            VALUES (%s, %s, %s, %s)
            """,
            (order_id, product_id, qty, unit_price)
        )

    print(
        f"[ORDER] Tạo đơn #{order_id} - "
        f"user {user_id} - "
        f"tổng {total_amount:,.0f}đ"
    )


def randomly_update_order_status():
    cur.execute(
        """
        SELECT id, status
        FROM orders
        WHERE status IN ('pending', 'confirmed', 'shipping')
        ORDER BY RANDOM()
        LIMIT 1
        """
    )

    row = cur.fetchone()

    if not row:
        return

    order_id, current_status = row

    next_status_map = {
        "pending": "confirmed",
        "confirmed": "shipping",
        "shipping": "delivered"
    }

    next_status = next_status_map.get(current_status)

    if next_status:
        cur.execute(
            """
            UPDATE orders
            SET status = %s,
                updated_at = NOW()
            WHERE id = %s
            """,
            (next_status, order_id)
        )

        print(
            f"[UPDATE] Đơn #{order_id}: "
            f"{current_status} -> {next_status}"
        )


if __name__ == "__main__":
    try:
        while True:
            action = random.choice(["create", "create", "update"])

            if action == "create":
                create_random_order()
            else:
                randomly_update_order_status()

            time.sleep(random.uniform(3, 8))

    finally:
        cur.close()
        conn.close()