package com.fngry.monk.biz.service.accounting.agg.config;

import java.util.List;
import java.util.Map;

public class AggJobConfig {

    private String jobId;

    private String bizName;

    private List<EntityAggConfig> entityAggConfigList;

    private Map<String, DataSet> enrichmentDataSet;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public List<EntityAggConfig> getEntityAggConfigList() {
        return entityAggConfigList;
    }

    public void setEntityAggConfigList(List<EntityAggConfig> entityAggConfigList) {
        this.entityAggConfigList = entityAggConfigList;
    }

}
