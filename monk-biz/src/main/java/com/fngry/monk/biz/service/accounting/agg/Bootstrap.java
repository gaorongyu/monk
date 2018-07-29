package com.fngry.monk.biz.service.accounting.agg;

import com.fngry.monk.biz.hbase.enums.ModelCodeEnum;
import com.fngry.monk.biz.hbase.model.MonkModel;
import com.fngry.monk.biz.service.accounting.agg.config.AggJobConfig;
import com.fngry.monk.biz.service.accounting.agg.config.EntityAggConfig;
import com.fngry.monk.biz.service.accounting.agg.task.MonkModelReadTask;
import com.fngry.monk.biz.service.accounting.common.AggEntity;
import com.fngry.monk.biz.service.accounting.common.SparkContextUtil;
import com.fngry.monk.common.hbase.annotation.HColumnFamily;
import org.apache.hadoop.conf.Configuration;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.Arrays;
import java.util.Iterator;

public class Bootstrap {


    private static final String APP_NAME = "AccountingAggregation";

    private static JavaSparkContext javaSparkContext;

    public static void main(String[] args) {
        System.out.println(APP_NAME + " agg job start!!! ");

        javaSparkContext = SparkContextUtil.initSparkContext(APP_NAME);

        AggJobConfig aggJobConfig = fetchAggJobConfig();

        try {
            aggJobConfig.getEntityAggConfigList()
                    .stream()
                    .map(e -> aggregationByEntity(e, javaSparkContext))
                    .toArray(JavaPairRDD[]::new);


        } catch (Exception e) {
            System.err.println(e);
            throw e;
        } finally {
            System.out.println(APP_NAME + " agg job end!!! ");
        }

    }

    private static JavaPairRDD<String, AggEntity> aggregationByEntity(EntityAggConfig entityAggConfig,
            JavaSparkContext javaSparkContext) {

        HColumnFamily columnFamily = entityAggConfig.getModelCode().getModelClass()
                .getAnnotation(HColumnFamily.class);

        // Scan scan = new Scan().withStartRow().withStopRow();

//        Configuration conf = HBaseConfiguration.create();
//        conf.set("hbase.zookeeper.quorum", SparkContextUtil.HBASE_ZOOKEEPER_QUORUM);
//        conf.set("hbase.mapreduce.inputtable", columnFamily.table());
//        conf.set("hbase.mapreduce.scan.column.family", columnFamily.value());

        JavaPairRDD readRDD = null;
//            javaSparkContext.newAPIHadoopRDD(conf, TableInputFormat.class,
//                ImmutableBytesWritable.class, Result.class);

        JavaRDD<MonkModel> modelRDD = readRDD.map(new MonkModelReadTask(entityAggConfig));

        modelRDD.foreachPartition(new VoidFunction<Iterator<MonkModel>>() {
            @Override
            public void call(Iterator<MonkModel> monkModelIterator) throws Exception {
                while (monkModelIterator.hasNext()) {
                    System.out.println(monkModelIterator.next());
                }
                System.out.println("aggregation......");
            }
        });


        return null;
    }

    private static AggJobConfig fetchAggJobConfig() {
        // for test
        AggJobConfig aggJobConfig = new AggJobConfig();

        EntityAggConfig entityAggConfig = new EntityAggConfig();
        entityAggConfig.setModelCode(ModelCodeEnum.BIZ_ORDER);

        aggJobConfig.setEntityAggConfigList(Arrays.asList(entityAggConfig));

        return aggJobConfig;
    }

}
