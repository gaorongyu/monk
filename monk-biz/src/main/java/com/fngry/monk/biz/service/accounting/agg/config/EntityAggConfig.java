package com.fngry.monk.biz.service.accounting.agg.config;

import com.fngry.monk.biz.hbase.enums.ModelCodeEnum;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EntityAggConfig implements Serializable {

    private String configId;

    private ModelCodeEnum modelCode;

    private List<EnrichmentRule> enrichmentRuleList;

    private Map<String, AggGroupingRule> aggGroupingRuleMap;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public ModelCodeEnum getModelCode() {
        return modelCode;
    }

    public void setModelCode(ModelCodeEnum modelCode) {
        this.modelCode = modelCode;
    }

    public List<EnrichmentRule> getEnrichmentRuleList() {
        return enrichmentRuleList;
    }

    public void setEnrichmentRuleList(List<EnrichmentRule> enrichmentRuleList) {
        this.enrichmentRuleList = enrichmentRuleList;
    }

    public Map<String, AggGroupingRule> getAggGroupingRuleMap() {
        return aggGroupingRuleMap;
    }

    public void setAggGroupingRuleMap(Map<String, AggGroupingRule> aggGroupingRuleMap) {
        this.aggGroupingRuleMap = aggGroupingRuleMap;
    }
}
