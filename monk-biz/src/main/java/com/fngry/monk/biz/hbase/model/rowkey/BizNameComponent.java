package com.fngry.monk.biz.hbase.model.rowkey;

import com.fngry.monk.common.hbase.rowkey.PlainComponent;

public class BizNameComponent extends PlainComponent {

    public BizNameComponent() {
        super("bizName", "bizName", 16);
    }

}
