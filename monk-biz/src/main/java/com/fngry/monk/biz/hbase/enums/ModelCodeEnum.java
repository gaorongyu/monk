package com.fngry.monk.biz.hbase.enums;

import com.fngry.monk.biz.hbase.model.BizOrder;
import com.fngry.monk.biz.hbase.model.MonkModel;
import com.fngry.monk.biz.hbase.model.rowkey.BizOrderRowKey;
import com.fngry.monk.common.hbase.rowkey.RowKey;

public enum ModelCodeEnum {

    BIZ_ORDER(BizOrder.class, BizOrderRowKey.class);

    private Class<? extends MonkModel> modelClass;

    private Class<? extends RowKey> rowKeyClass;

    ModelCodeEnum(Class<? extends MonkModel> modelClass, Class<? extends RowKey> rowKeyClass) {
        this.modelClass = modelClass;
        this.rowKeyClass = rowKeyClass;
    }

    public static ModelCodeEnum get(Class<? extends MonkModel> modelClass) {
        for (ModelCodeEnum e : ModelCodeEnum.values()) {
            if (e.modelClass == modelClass) {
                return e;
            }
        }
        return null;
    }

    public Class<? extends MonkModel> getModelClass() {
        return modelClass;
    }

    public void setModelClass(Class<? extends MonkModel> modelClass) {
        this.modelClass = modelClass;
    }

    public Class<? extends RowKey> getRowKeyClass() {
        return rowKeyClass;
    }

    public void setRowKeyClass(Class<? extends RowKey> rowKeyClass) {
        this.rowKeyClass = rowKeyClass;
    }
}
