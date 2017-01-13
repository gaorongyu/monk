package com.fngry.monk.biz.demo.searchengin.elasticsearch.search;

import com.fngry.monk.biz.demo.searchengin.elasticsearch.common.ElasticFactory;
import com.fngry.monk.biz.demo.searchengin.elasticsearch.common.IndexDefinition;
import com.fngry.monk.biz.demo.searchengin.elasticsearch.search.request.OrderQueryRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

/**
 *
 *
 *
 * Created by gaorongyu on 16/12/22.
 */
@Service
public class OrderInfoQueryHandler {

    public String queryOrderInfo(OrderQueryRequest request) {

        SearchRequestBuilder searchBuilder = ElasticFactory.getClient()
                .prepareSearch(IndexDefinition.ORDER_INFO.getName())
                .setTypes(IndexDefinition.ORDER_INFO.getType());

        searchBuilder.setSearchType(SearchType.QUERY_AND_FETCH);
        searchBuilder.setFrom(0);
        searchBuilder.setSize(1000);
        searchBuilder.setExplain(true);

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // 字母小写才搜索的到
        queryBuilder.must(QueryBuilders
                .termQuery("order_sn", request.getOrderSn().toLowerCase()));
        searchBuilder.setQuery(queryBuilder);

        SearchResponse response = searchBuilder.execute().actionGet();

        StringBuffer msg = new StringBuffer();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            String obj = hit.getSourceAsString();
            msg.append(obj);
        }

        return msg.toString();
    }

}
