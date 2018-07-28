package com.fngry.monk.biz.hbase.model;

import com.fngry.monk.biz.hbase.enums.ModelCodeEnum;
import com.fngry.monk.common.hbase.HbaseModelSupport;
import com.fngry.monk.common.hbase.rowkey.RowKey;

public abstract class MonkModelSupport extends HbaseModelSupport implements MonkModel {
    @Override
    protected Class<? extends RowKey> getRowKeyClass() {
        ModelCodeEnum modelCode = ModelCodeEnum.get(this.getClass());
        return modelCode.getRowKeyClass();
    }
}
