package com.fngry.monk.biz.hbase.processor.impl;

import com.fngry.monk.biz.hbase.model.BizOrder;
import com.fngry.monk.biz.hbase.model.MonkModel;
import com.fngry.monk.biz.hbase.model.rowkey.BizOrderRowKey;
import com.fngry.monk.biz.hbase.processor.ModelProcessor;
import com.fngry.monk.common.hbase.HbaseClient;

public class BizOrderProcessor implements ModelProcessor {

    @Override
    public void process(MonkModel monkModel, HbaseClient hbaseClient) {

        BizOrder bizOrder = (BizOrder) monkModel;

        BizOrderRowKey parentOrderRowKey = new BizOrderRowKey();
        parentOrderRowKey.setBizName(bizOrder.getBizName());
        parentOrderRowKey.setSource(bizOrder.getParentSource());
        parentOrderRowKey.setOrderNo(bizOrder.getParentOrderNo());

        bizOrder.setParentOrderRowKey(parentOrderRowKey.resolve());

        hbaseClient.upsert(bizOrder);
    }

}
