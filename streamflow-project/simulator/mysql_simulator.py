import mysql.connector
import random
import time

conn = mysql.connector.connect(
    host="localhost",
    port=3306,
    database="logistics",
    user="admin",
    password="anh0174"
)

conn.autocommit = True
cur = conn.cursor()

CITY_CENTERS = {
    "Hồ Chí Minh": (10.7756, 106.7019),
    "Hà Nội": (21.0285, 105.8048),
    "Đà Nẵng": (16.0544, 108.2022),
}


def update_driver_location():

    cur.execute("""
        SELECT id, city
        FROM drivers
        WHERE status != 'off'
    """)

    active_drivers = cur.fetchall()

    if not active_drivers:
        return

    driver_id, city = random.choice(active_drivers)

    base_lat, base_lng = CITY_CENTERS.get(city,(10.7756, 106.7019))

    lat = base_lat + random.uniform(-0.02, 0.02)
    lng = base_lng + random.uniform(-0.02, 0.02)
    speed = round(random.uniform(0, 45), 1)

    cur.execute(
        """
        INSERT INTO driver_locations
            (driver_id, lat, lng, speed_kmh)
        VALUES (%s, %s, %s, %s)
        """,
        (driver_id, lat, lng, speed)
    )

    print(
        f"[GPS] Tài xế #{driver_id} "
        f"({city}): {lat:.4f}, {lng:.4f} - "
        f"{speed} km/h"
    )


def randomly_update_shipment_status():

    cur.execute(
        """
        SELECT id, status
        FROM shipments
        WHERE status IN ('pending','assigned','picked_up','in_transit')
        ORDER BY RAND()
        LIMIT 1
        """
    )

    row = cur.fetchone()

    if not row:
        return

    shipment_id, current_status = row

    next_status_map = {
        "pending": "assigned",
        "assigned": "picked_up",
        "picked_up": "in_transit",
        "in_transit": "delivered"
    }

    next_status = next_status_map.get(current_status)

    if not next_status:
        return

    # Pending -> Assigned
    if current_status == "pending":

        cur.execute(
            """
            SELECT id
            FROM drivers
            WHERE status = 'available'
            ORDER BY RAND()
            LIMIT 1
            """
        )

        driver_row = cur.fetchone()

        if not driver_row:
            return

        driver_id = driver_row[0]

        cur.execute(
            """
            UPDATE shipments
            SET status = %s,
                driver_id = %s
            WHERE id = %s
            """,
            (next_status, driver_id, shipment_id)
        )

        cur.execute(
            """
            UPDATE drivers
            SET status = 'on_delivery'
            WHERE id = %s
            """,
            (driver_id,)
        )

    # In transit -> Delivered
    elif current_status == "in_transit":

        cur.execute(
            """
            SELECT driver_id
            FROM shipments
            WHERE id = %s
            """,
            (shipment_id,)
        )

        driver_row = cur.fetchone()
        driver_id = driver_row[0] if driver_row else None

        cur.execute(
            """
            UPDATE shipments
            SET status = %s
            WHERE id = %s
            """,
            (next_status, shipment_id)
        )

        if driver_id:
            cur.execute(
                """
                UPDATE drivers
                SET status = 'available'
                WHERE id = %s
                """,
                (driver_id,)
            )

    # Assigned -> Picked up
    # Picked up -> In transit
    else:

        cur.execute(
            """
            UPDATE shipments
            SET status = %s
            WHERE id = %s
            """,
            (next_status, shipment_id)
        )

    print(
        f"[SHIPMENT] Đơn vận #{shipment_id}: "
        f"{current_status} -> {next_status}"
    )


if __name__ == "__main__":
    try:
        while True:
            action = random.choice(["gps", "gps", "gps", "shipment"])
            if action == "gps":
                update_driver_location()
            else:
                randomly_update_shipment_status()

            time.sleep(random.uniform(2, 5))
    finally:
        cur.close()
        conn.close()