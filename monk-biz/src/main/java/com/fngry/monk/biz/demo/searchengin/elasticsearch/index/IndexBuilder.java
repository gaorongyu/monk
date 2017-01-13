package com.fngry.monk.biz.demo.searchengin.elasticsearch.index;

import com.fngry.monk.biz.demo.searchengin.elasticsearch.common.ElasticFactory;
import com.fngry.monk.common.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaorongyu on 17/1/4.
 */
@Slf4j
public class IndexBuilder {

    private static final String PRIMARY_KEY = "primary_key";

    private static final int PAGE_SIZE = 10000;


    public static boolean bulkInsertData(String indexName, String indexType, String indexField, List<Map<String, Object>> docList) throws Exception {
        if (docList != null && !docList.isEmpty()) {
            List<List<Map<String, Object>>> divideList = CollectionUtil.divideList(docList, PAGE_SIZE);
            for (List<Map<String, Object>> contentList : divideList) {
                Map<String, XContentBuilder> xMap = makeXContent(indexField, contentList);
                // 执行插入
                _bulkInsertData(indexName, indexType, xMap, false);
            }
        }
        return true;
    }

    private static boolean _bulkInsertData(String indexName, String indexType,  Map<String, XContentBuilder> xMap, boolean refresh) throws Exception {
        final Boolean[] result = {Boolean.FALSE};
        if (xMap.size() <= 0) {
            return false;
        }
        try {
            BulkProcessor bulkProcessor = BulkProcessor
                    .builder(ElasticFactory.getClient(), new BulkProcessor.Listener() {

                @Override
                public void beforeBulk(long executionId, BulkRequest request) {

                }

                @Override
                public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                    if (!response.hasFailures()) {
                        result[0] = true;
                    } else {
                        log.error(response.buildFailureMessage());
                    }
                }

                @Override
                public void afterBulk(long executionId, BulkRequest request, Throwable failure) {

                }

            })
            .setBulkSize(new ByteSizeValue(500, ByteSizeUnit.MB))
            .setBulkActions(-1)
            .setConcurrentRequests(0)
            .build();

            for (Map.Entry<String, XContentBuilder> entry : xMap.entrySet()) {
                bulkProcessor.add(Requests.indexRequest(indexName)
                        .type(indexType).id(entry.getKey()).source(entry.getValue()));
            }
            bulkProcessor.flush();
            bulkProcessor.close();
            if (refresh) {
                doRefresh(indexName);
            }
            return result[0];
        } catch (Exception e) {
            e.printStackTrace();
            log.error("_bulkInsertData {} exception {}", indexType, e);
            throw e;
        }
    }

    private static Map<String, XContentBuilder> makeXContent(String indexField, List<Map<String, Object>> contentList) {
        Map<String, XContentBuilder> xMap = new HashMap<>();
        for (Map<String, Object> c : contentList) {
            XContentBuilder xContentBuilder;
            try {
                xContentBuilder = XContentFactory.jsonBuilder().startObject();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return null;
            }
            for (Map.Entry<String, Object> entry : c.entrySet()) {
                if (entry.getKey().equals(PRIMARY_KEY)) {
                    continue;
                }
                String field = entry.getKey();
                Object values = entry.getValue();
                try {
                    xContentBuilder.field(field, values);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                xContentBuilder.endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
            xMap.put(String.valueOf(c.get(indexField)), xContentBuilder);
        }
        return xMap;
    }

    public static void doRefresh(String... indices) throws ElasticsearchException {
        if (indices == null || indices.length == 0) {
            return;
        }
        long startTime = System.currentTimeMillis();
        try {
            ElasticFactory.getClient().admin().indices().prepareRefresh(indices).get();
            log.info("refresh index used:" + (System.currentTimeMillis() - startTime) + "ms");
        } catch (ElasticsearchException e) {
            log.error("执行索引: " + Arrays.asList(indices) + " refresh操作发生异常", e);
            throw e;
        }
    }

}
