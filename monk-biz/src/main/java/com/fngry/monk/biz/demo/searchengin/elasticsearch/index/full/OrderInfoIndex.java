package com.fngry.monk.biz.demo.searchengin.elasticsearch.index.full;

import com.fngry.monk.biz.demo.searchengin.elasticsearch.index.IndexBuilder;
import com.fngry.monk.biz.util.IndexUtil;
import com.fngry.monk.dao.test.OrderInfoMapper;
import com.fngry.monk.dao.test.OrderInfoQueryParam;
import com.fngry.monk.entity.test.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gaorongyu on 16/12/14.
 */
@Component
public class OrderInfoIndex extends AbstractFullIndex {

    public static final String INDEX_NAME = "order_info";

    public static final String INDEX_TYPE = "order_info";

    public static final String PRIMARY_KEY = "id";

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public void makeIndex() {
        // select from db
        OrderInfoQueryParam param = new OrderInfoQueryParam();
        List<OrderInfo> orderInfoList = orderInfoMapper.select(param);
        // make docs
        List<Map<String, Object>> docs = new ArrayList<>();
        for (OrderInfo order : orderInfoList) {
            Map<String, Object> doc = IndexUtil.makeIndexDoc(true, PRIMARY_KEY, order);
            docs.add(doc);
        }
        // make index
        try {
            IndexBuilder.bulkInsertData(INDEX_NAME, INDEX_TYPE, PRIMARY_KEY, docs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
