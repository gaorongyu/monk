package com.fngry.monk.biz.hbase.processor;

import com.fngry.monk.biz.hbase.model.MonkModel;
import com.fngry.monk.common.hbase.HbaseClient;

import java.io.Serializable;

public interface ModelProcessor extends Serializable {

    void process(MonkModel model, HbaseClient hbaseClient);

}
