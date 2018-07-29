package com.fngry.monk.biz.service.accounting.agg.task;

import com.fngry.monk.biz.hbase.model.MonkModel;
import com.fngry.monk.biz.service.accounting.agg.config.EntityAggConfig;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

public class MonkModelReadTask implements Function<Tuple2<ImmutableBytesWritable, Result>, MonkModel> {

    private static final long serialVersionUID = 5429164561026064430L;

    private EntityAggConfig entityAggConfig;

    public MonkModelReadTask(EntityAggConfig entityAggConfig) {
        this.entityAggConfig = entityAggConfig;
    }

    @Override
    public MonkModel call(Tuple2<ImmutableBytesWritable, Result> v1) throws Exception {
        MonkModel model = entityAggConfig.getModelCode().getModelClass().newInstance();
        model.read(v1._2());
        return model;
    }

}
