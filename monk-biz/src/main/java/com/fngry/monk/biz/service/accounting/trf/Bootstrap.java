package com.fngry.monk.biz.service.accounting.trf;

import com.fngry.monk.biz.hbase.model.MonkModel;
import com.fngry.monk.biz.service.accounting.common.BizEntity;
import com.fngry.monk.biz.service.accounting.trf.config.EntityConfig;
import com.fngry.monk.biz.service.accounting.trf.config.TrfConfig;
import com.fngry.monk.biz.service.accounting.trf.config.TrfJobConfig;
import com.fngry.monk.biz.service.accounting.trf.task.ModelProcessTask;
import com.fngry.monk.biz.service.accounting.trf.task.MysqlResultConvertTask;
import com.fngry.monk.biz.service.accounting.trf.task.TransformTask;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.JdbcRDD;

import java.sql.DriverManager;

public class Bootstrap {

    private static final String APP_NAME = "AccountingTransformation";

    private static JavaSparkContext javaSparkContext;

    public static void main(String[] args) {
        System.out.println(APP_NAME + " job start!!! ");

        SparkConf sparkConf = new SparkConf().setAppName(APP_NAME);
        javaSparkContext = new JavaSparkContext(sparkConf);

        TrfJobConfig trfJobConfig = fetchTrfJobConfig();

        try {
            doTransform(trfJobConfig);
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        } finally {
            // todo update job status
            System.out.println(APP_NAME + " job end!!! ");
        }

    }

    private static void doTransform(TrfJobConfig trfJobConfig) {
        TrfConfig trfConfig = trfJobConfig.getTrfConfig();

        // 读取数据源
        String sql = "select * from " + trfConfig.getEntityConfig().getTableName() + " where id >= ? and id <= ? ";
        JavaRDD<BizEntity> bizEntityRDD = JdbcRDD.create(
                javaSparkContext,
                ConnectionFactory.createConnection(trfConfig),
                sql,
                0L,
                1000L,
                1,
                new MysqlResultConvertTask());
        // 校验

        // 字段转换
        JavaRDD<MonkModel> modelRDD = bizEntityRDD.flatMap(new TransformTask(trfJobConfig.getJobId(), trfConfig));
        // 持久化
        modelRDD.foreachPartition(new ModelProcessTask());
    }

    private static TrfJobConfig fetchTrfJobConfig() {
        TrfJobConfig trfJobConfig = new TrfJobConfig();

        TrfConfig trfConfig = new TrfConfig();
        EntityConfig entityConfig = new EntityConfig();
        entityConfig.setTableName("wy_orderinfo");
        trfConfig.setEntityConfig(entityConfig);

        trfJobConfig.setTrfConfig(trfConfig);

        return trfJobConfig;
    }

    private static class ConnectionFactory {

        public static JdbcRDD.ConnectionFactory createConnection(TrfConfig trfConfig) {
            return (JdbcRDD.ConnectionFactory) () -> {
                Class.forName("com.mysql.jdbc.Driver");
                return DriverManager.getConnection(
                        "",
                        "",
                        "");
            };
        }

    }

}
