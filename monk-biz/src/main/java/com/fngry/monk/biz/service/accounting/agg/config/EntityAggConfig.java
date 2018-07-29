package com.fngry.monk.biz.service.accounting.agg.config;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EntityAggConfig implements Serializable {

    private String configId;

    private String modelCode;

    private List<EnrichmentRule> enrichmentRuleList;

    private Map<String, AggGroupingRule> aggGroupingRuleMap;


}
