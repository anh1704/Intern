package com.batchflow.extract.writer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

public class MinioParquetWriter implements DataWriter {
    @Override
    public void write(Dataset<Row> data, String destination) {
        data.write()
                .mode(SaveMode.Overwrite)   // ghi đè
                .parquet("s3a://" + destination);
    }
}
