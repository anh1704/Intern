package com.batchflow.extract;

import com.batchflow.extract.reader.HdfsCsvReader;
import com.batchflow.extract.reader.MinioCsvReader;
import com.batchflow.extract.reader.MinioParquetReader;
import com.batchflow.extract.transform.OrderTransformer;
import com.batchflow.extract.writer.HdfsParquetWriter;
import com.batchflow.extract.writer.MinioCsvWriter;
import com.batchflow.extract.writer.MinioParquetWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import com.batchflow.extract.reader.PostgresReader;
import com.batchflow.extract.session.SparkSessionFactory;

public class Main {
    public static void main(String[] args) {
        SparkSession spark = SparkSessionFactory.createSession();

        PostgresReader postgresReader = new PostgresReader(spark);
        MinioCsvReader minioReader = new MinioCsvReader(spark);
        MinioParquetReader minioParquetReader = new MinioParquetReader(spark);
        HdfsCsvReader hdfsReader = new HdfsCsvReader(spark);

        Dataset<Row> customers = postgresReader.read("customers");
        Dataset<Row> orders = postgresReader.read("orders");
        Dataset<Row> products = minioReader.read("raw-data/products.csv");
        Dataset<Row> regions = hdfsReader.read("/raw-data/regions.csv");

        OrderTransformer orderTransformer = new OrderTransformer(spark);

        Dataset<Row> joined = orderTransformer.joinOrdersWithProductsUsingSql(orders, products);
        Dataset<Row> bigOrders = orderTransformer.filterByMinAmount(joined, 1000.0);
        Dataset<Row> spending = orderTransformer.aggregateByCustomer(joined);

        MinioParquetWriter parquetWriter = new MinioParquetWriter();
        parquetWriter.write(joined, "processed-data/orders_joined_parquet");
        System.out.println("Đã ghi data vào processed-data/orders_joined_parquet ");

        MinioCsvWriter csvWriter = new MinioCsvWriter();
        csvWriter.write(spending, "processed-data/customer_spending_csv");
        System.out.println("Đã ghi xong data vào processed-data/customer_spending_csv");

        Dataset<Row> df = minioParquetReader.read("processed-data/orders_joined_parquet");

        HdfsParquetWriter hdfsWriter = new HdfsParquetWriter();
        hdfsWriter.write(joined, "/processed-data/orders_joined_parquet");
        System.out.println("Đã ghi xong Parquet vào HDFS: /processed-data/orders_joined_parquet");
    }
}
