package com.batchflow.extract.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class HdfsCsvReader implements DataReader{

    private final SparkSession spark;

    public HdfsCsvReader(SparkSession spark) {
        this.spark = spark;
    }

    @Override
    public Dataset<Row> read(String source) {
        return spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .csv("hdfs://namenode:9000" + source);
    }
}
