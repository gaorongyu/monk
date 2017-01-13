package com.fngry.monk.biz.demo.searchengin.elasticsearch.common;

import lombok.Data;

/**
 * Created by gaorongyu on 16/12/22.
 */
@Data
public class IndexDefinition {

    private String name;

    private String type;

    private IndexDefinition(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public static final IndexDefinition VENUS_ORDER_INFO = new IndexDefinition("order_center_order_info", "order_center_order_info");
    public static final IndexDefinition ORDER_INFO = new IndexDefinition("order_info", "order_info");

}
