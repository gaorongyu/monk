package com.fngry.monk.biz.hbase.processor;

import com.alibaba.fastjson.JSON;
import com.fngry.monk.biz.hbase.model.MonkModel;
import com.fngry.monk.common.hbase.HbaseClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * model process executor
 */
public class ModelProcessorExecutor {

    private static Map<String, ModelProcessor> modelProcessors = new ConcurrentHashMap<>();

    public static void process(MonkModel model, HbaseClient hbaseClient) {
        ModelProcessor processor = getModelProcessor(model);
        try {
            processor.process(model, hbaseClient);
        } catch (Exception e) {
            throw new RuntimeException(String.format("fail to process model %s, %s",
                    model.getClass().getName(), JSON.toJSONString(model)), e);
        }

    }

    private static ModelProcessor getModelProcessor(MonkModel model) {
        MonkModelProcessor monkModelProcessor = model.getClass().getAnnotation(MonkModelProcessor.class);
        if (monkModelProcessor == null) {
            throw new RuntimeException(String.format("model %s not define processor", model.getClass()));
        }

        String processorName = monkModelProcessor.target().getName();
        ModelProcessor processor = modelProcessors.get(processorName);
        if (processor == null) {
            try {
                processor = monkModelProcessor.target().newInstance();
                modelProcessors.put(processorName, processor);
            } catch (Exception e) {
                throw new RuntimeException(String.format("fail to new instance of %", processorName), e);
            }

        }
        return processor;
    }

}
