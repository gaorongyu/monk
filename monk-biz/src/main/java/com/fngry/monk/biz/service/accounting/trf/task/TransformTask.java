package com.fngry.monk.biz.service.accounting.trf.task;

import com.fngry.monk.biz.hbase.model.BizOrder;
import com.fngry.monk.biz.hbase.model.MonkModel;
import com.fngry.monk.biz.service.accounting.common.BizEntity;
import com.fngry.monk.biz.service.accounting.trf.config.TrfConfig;
import com.fngry.monk.biz.service.accounting.trf.config.TrfModelConfig;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TransformTask implements FlatMapFunction<BizEntity, MonkModel> {

    private static final long serialVersionUID = -4592418461911845577L;

    private String jobId;

    private TrfConfig trfConfig;

    private transient Collection<EntityTransformer> entityTransformers;

    public TransformTask(String jobId, TrfConfig trfConfig) {

    }

    @Override
    public Iterator<MonkModel> call(BizEntity bizEntity) throws Exception {
        return getEntityTransforerList().stream()
                .filter(e -> e.matches(bizEntity))
                .map(e -> transform(bizEntity, e))
                .iterator();
    }

    private MonkModel transform(BizEntity bizEntity, EntityTransformer transformer) {
        System.out.println("bizEntity: " + bizEntity.getData());

        BizOrder bizOrder = new BizOrder();
        bizOrder.setOrderNo(String.valueOf(bizEntity.getData().get("id")));
        bizOrder.setSource("fngry");
        bizOrder.setBizName("wyOrder");

        bizOrder.setParentOrderNo("orderNo-001");
        bizOrder.setParentSource("fngry");

        bizOrder.setSellerId(String.valueOf(bizEntity.getData().get("userid")));
        bizOrder.setRemark(String.valueOf(bizEntity.getData().get("bankcode")));

        return bizOrder;
    }

    private Collection<EntityTransformer> getEntityTransforerList() {
        // todo
//        entityTransformers = trfConfig.getTrfModelConfigList()
//                .stream()
//                .map(EntityTransformer::create)
//                .collect(Collectors.toList());
        entityTransformers = new ArrayList<>();
        entityTransformers.add(EntityTransformer.create(null));

        return entityTransformers;
    }

    private static class EntityTransformer {

        private TrfModelConfig trfModelConfig;

        public EntityTransformer(TrfModelConfig trfModelConfig) {

        }

        public static EntityTransformer create(TrfModelConfig trfModelConfig) {
            EntityTransformer transformer = new EntityTransformer(trfModelConfig);

            return transformer;
        }

        public boolean matches(BizEntity bizEntity) {

            return true;
        }



    }

}
