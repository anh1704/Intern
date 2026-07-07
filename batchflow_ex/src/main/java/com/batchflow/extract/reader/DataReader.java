package com.batchflow.extract.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface DataReader {

    /**
     * @param source Đường dẫn (URI) tới file hoặc thư mục chứa dữ liệu
     */
    Dataset<Row> read(String source);
}
