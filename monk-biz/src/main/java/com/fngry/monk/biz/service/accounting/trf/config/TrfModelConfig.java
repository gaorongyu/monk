package com.fngry.monk.biz.service.accounting.trf.config;

import com.fngry.monk.biz.service.accounting.common.config.FilterRule;

import java.util.List;

public class TrfModelConfig {

    private String configId;

    private String configName;

    private String modelCode;

    private List<FilterRule> filterRuleList;

    private List<TransformRule> transformRuleList;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public List<FilterRule> getFilterRuleList() {
        return filterRuleList;
    }

    public void setFilterRuleList(List<FilterRule> filterRuleList) {
        this.filterRuleList = filterRuleList;
    }

    public List<TransformRule> getTransformRuleList() {
        return transformRuleList;
    }

    public void setTransformRuleList(List<TransformRule> transformRuleList) {
        this.transformRuleList = transformRuleList;
    }
}
