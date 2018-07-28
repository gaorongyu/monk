package com.fngry.monk.biz.hbase.model.rowkey;

import com.fngry.monk.common.hbase.rowkey.AbstractRowKey;
import com.fngry.monk.common.hbase.rowkey.MD5Component;
import com.fngry.monk.common.hbase.rowkey.RowKeyComponent;

public class BizOrderRowKey extends AbstractRowKey {

    private static RowKeyComponent[] COMPONENTS = {
            new BizNameComponent(),
            new MD5Component("sourceOrderNo", "source", "orderNo")
    };

    private String bizName;

    private String source;

    private String orderNo;

    public BizOrderRowKey() {
        super(COMPONENTS);
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}
