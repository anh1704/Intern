package com.batchflow.extract.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class MinioParquetReader implements DataReader{

    private final SparkSession spark;

    public MinioParquetReader(SparkSession spark) {
        this.spark = spark;
    }

    @Override
    public Dataset<Row> read(String source) {
        return spark.read()
                .parquet("s3a://" + source);
    }
}
