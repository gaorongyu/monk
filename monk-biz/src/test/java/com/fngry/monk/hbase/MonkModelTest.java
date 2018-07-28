package com.fngry.monk.hbase;

import com.fngry.monk.biz.hbase.enums.ModelCodeEnum;
import com.fngry.monk.biz.hbase.model.BizOrder;
import com.fngry.monk.common.hbase.HbaseClient;
import com.fngry.monk.common.hbase.impl.HBaseClientImpl;
import com.fngry.monk.common.hbase.rowkey.RowKey;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonkModelTest {

    @Test
    public void testInsertBizOrder() {
        HbaseClient hbaseClient = new HBaseClientImpl();

        BizOrder bizOrder = new BizOrder();

        bizOrder.setAmount(new BigDecimal("23.45"));
        bizOrder.setBizName("testBizName");
        bizOrder.setOrderNo("orderNo-002");
        bizOrder.setSource("fngry");

        bizOrder.setParentOrderNo("orderNo-001");
        bizOrder.setParentSource("fngry");

        byte[] rowKey = hbaseClient.upsert(bizOrder);
        System.out.println(rowKey);
    }

    @Test
    public void testScantBizOrder() throws Exception {
        HbaseClient hbaseClient = new HBaseClientImpl();

        // query params
        Map<String, Object> params = new HashMap<>();
//        params.put("", "");
        int limit = 2000;

        ModelCodeEnum modelCodeEnum = ModelCodeEnum.BIZ_ORDER;
        RowKey rowKey = modelCodeEnum.getRowKeyClass().newInstance();
        rowKey.set(params);
        Pair<byte[], byte[]> rowKeyRange = rowKey.resolveScan(params.size());

        List bizOrders = hbaseClient.scan(
                modelCodeEnum.getModelClass(), rowKeyRange.getLeft(), rowKeyRange.getRight(), limit);

        System.out.println(bizOrders);
    }

    @Test
    public void testSelectBizOrderByRowKey() throws Exception {
        HbaseClient hbaseClient = new HBaseClientImpl();

        BizOrder bizOrder = new BizOrder();
        bizOrder.setBizName("testBizName");
        bizOrder.setSource("fngry");
        bizOrder.setOrderNo("orderNo-002");

        byte[] rowKey = bizOrder.resolveRowkey();

        BizOrder bizOrderResult = hbaseClient.select(bizOrder.getClass(), rowKey);

        System.out.println(bizOrderResult);
        Assert.assertEquals(new BigDecimal("23.45"), bizOrderResult.getAmount());
    }

}
