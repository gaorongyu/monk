package com.fngry.monk.biz.service.accounting.common;

import java.io.Serializable;
import java.util.Map;

public class BizEntity implements Serializable {

    private static final long serialVersionUID = -7667470126571044253L;

    private Map<String, Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
