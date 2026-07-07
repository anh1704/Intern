package com.batchflow.extract.writer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface DataWriter {
    void write(Dataset<Row> data, String destination);
}
