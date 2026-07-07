package com.batchflow.extract.transform;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.*;

public class OrderTransformer {

    private final SparkSession spark;

    public OrderTransformer(SparkSession spark) {
        this.spark = spark;
    }

    /**
     * Join orders (Postgres) với products (MinIO) qua product_name,
     * tính tổng tiền từng dòng, lấy thêm category từ products.
     */
    public Dataset<Row> joinOrdersWithProducts(Dataset<Row> orders, Dataset<Row> products) {
        Dataset<Row> joined = orders
                .join(products, orders.col("product_name").equalTo(products.col("product_name")))
                .select(
                        orders.col("order_id"),
                        orders.col("customer_id"),
                        orders.col("product_name"),
                        products.col("category"),
                        orders.col("unit_price"),
                        orders.col("quantity").multiply(orders.col("unit_price")).alias("total_amount"),
                        orders.col("order_date")
                );
        return joined;
    }

    /**
     * Dùng Spark SQL
     */
    public Dataset<Row> joinOrdersWithProductsUsingSql(Dataset<Row> orders, Dataset<Row> products) {
        orders.createOrReplaceTempView("orders");
        products.createOrReplaceTempView("products");

        String sql =
                "SELECT " +
                        "o.order_id, " +
                        "o.customer_id, " +
                        "o.product_name, " +
                        "p.category, " +
                        "o.quantity, " +
                        "o.unit_price, " +
                        "(o.quantity * o.unit_price) AS total_amount, " +
                        "o.order_date " +
                        "FROM orders o " +
                        "JOIN products p " +
                        "ON o.product_name = p.product_name";

        return spark.sql(sql);
    }

    /**
     * Filter: chỉ lấy đơn hàng có total_amount > minAmount
     */
    public Dataset<Row> filterByMinAmount(Dataset<Row> joinedOrders, double minAmount) {
        return joinedOrders.filter(joinedOrders.col("total_amount").gt(minAmount));
    }

    /**
     * Tổng tiền theo từng customer_id
     */
    public Dataset<Row> aggregateByCustomer(Dataset<Row> joinedOrders){
        return joinedOrders
                .groupBy("customer_id")
                .agg(sum("total_amount").alias("total_spent"))
                .orderBy(desc("total_spent"));
    }

}
