package com.fngry.monk.biz.demo.searchengin.elasticsearch.search.request;

import lombok.Data;

/**
 * Created by gaorongyu on 16/12/22.
 */
@Data
public class OrderQueryRequest {

    private Integer orderId;

    private String orderSn;

    private String companyName;

}
