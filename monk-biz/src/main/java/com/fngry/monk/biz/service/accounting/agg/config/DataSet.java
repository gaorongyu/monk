package com.fngry.monk.biz.service.accounting.agg.config;

import java.io.Serializable;
import java.util.List;

public class DataSet<T extends EnrichmentData> implements Serializable{

    private String enrichmentRuleCode;

    private List<T> data;

    public String getEnrichmentRuleCode() {
        return enrichmentRuleCode;
    }

    public void setEnrichmentRuleCode(String enrichmentRuleCode) {
        this.enrichmentRuleCode = enrichmentRuleCode;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
