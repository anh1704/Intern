package com.batchflow.extract.session;

import org.apache.spark.sql.SparkSession;

public class SparkSessionFactory {

    private SparkSessionFactory(){}

    public static SparkSession createSession(){
        return SparkSession.builder()
                .appName("Batchflow Extract")
                //.master("local[*]")
                .master("spark://spark-master:7077")
                //.config("spark.hadoop.fs.s3a.endpoint", "http://localhost:9000")
                .config("spark.hadoop.fs.s3a.endpoint", "http://minio:9000")
                .config("spark.hadoop.fs.s3a.access.key", "minioadmin")
                .config("spark.hadoop.fs.s3a.secret.key", "minioadmin123")
                .config("spark.hadoop.fs.s3a.path.style.access", "true")
                .config("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
                .config("spark.hadoop.fs.s3a.connection.ssl.enabled", "false")
                .getOrCreate();
    }
}
