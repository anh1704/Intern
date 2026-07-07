package com.batchflow.extract.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class PostgresReader implements DataReader{

    private final SparkSession spark;

    public PostgresReader(SparkSession spark) {
        this.spark = spark;
    }

    @Override
    public Dataset<Row> read(String source) {
        String jdbcUrl = "jdbc:postgresql://postgres-db:5432/batchflow_db_ex";
        //String jdbcUrl = "jdbc:postgresql://localhost:5432/batchflow_db_ex";

        Properties connectionProp = new Properties();
        connectionProp.put("user", "admin");
        connectionProp.put("password", "anh0174");
        connectionProp.put("driver", "org.postgresql.Driver");

        return spark.read()
                .jdbc(
                        jdbcUrl,
                        source,
                        connectionProp
                );
    }
}
