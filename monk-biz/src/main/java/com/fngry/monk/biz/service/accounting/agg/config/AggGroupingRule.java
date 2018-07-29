package com.fngry.monk.biz.service.accounting.agg.config;

import java.io.Serializable;
import java.util.List;

public class AggGroupingRule implements Serializable {

    private List<String> dimensionFieldNameList;

    private List<String> aggregationFieldNameList;

    public List<String> getDimensionFieldNameList() {
        return dimensionFieldNameList;
    }

    public void setDimensionFieldNameList(List<String> dimensionFieldNameList) {
        this.dimensionFieldNameList = dimensionFieldNameList;
    }

    public List<String> getAggregationFieldNameList() {
        return aggregationFieldNameList;
    }

    public void setAggregationFieldNameList(List<String> aggregationFieldNameList) {
        this.aggregationFieldNameList = aggregationFieldNameList;
    }

}
