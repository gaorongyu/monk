package com.fngry.monk.biz.service.accounting.common;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkContextUtil {

    public static final String HBASE_ZOOKEEPER_QUORUM = "localhost:2181";

    public static JavaSparkContext initSparkContext(String appName) {
        SparkConf sparkConf = new SparkConf().setAppName(appName)
                .setMaster("local");
        return new JavaSparkContext(sparkConf);
    }

}
