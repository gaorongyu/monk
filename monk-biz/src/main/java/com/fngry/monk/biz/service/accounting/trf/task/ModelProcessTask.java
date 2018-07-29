package com.fngry.monk.biz.service.accounting.trf.task;

import com.fngry.monk.biz.hbase.model.MonkModel;
import com.fngry.monk.biz.hbase.processor.ModelProcessorExecutor;
import com.fngry.monk.common.hbase.HbaseClient;
import com.fngry.monk.common.hbase.impl.HBaseClientImpl;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.Iterator;

public class ModelProcessTask implements VoidFunction<Iterator<MonkModel>> {

    @Override
    public void call(Iterator<MonkModel> monkModelIterator) throws Exception {
        try {
            HbaseClient hbaseClient = new HBaseClientImpl();
            while (monkModelIterator.hasNext()) {
                MonkModel model = monkModelIterator.next();
                System.out.println("monkModel: " + model);
                ModelProcessorExecutor.process(model, hbaseClient);
            }
        } catch (Exception e) {
            String errorMsg = String.format("model process error %s", e.getMessage());
            System.err.println(errorMsg);
            throw new RuntimeException(errorMsg, e);
        } finally {
            System.out.println("process........");
        }
    }

}
