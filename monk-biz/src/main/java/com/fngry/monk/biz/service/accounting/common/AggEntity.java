package com.fngry.monk.biz.service.accounting.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class AggEntity implements Serializable {

    private static final long serialVersionUID = -67724922262817684L;

    private String modelCode;

    private String acctCode;

    private String currency;

    private BigDecimal amount;

    private Map<String, Object> dimensions;

    private String groupingKey;

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Map<String, Object> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Map<String, Object> dimensions) {
        this.dimensions = dimensions;
    }

    public String getGroupingKey() {
        return groupingKey;
    }

    public void setGroupingKey(String groupingKey) {
        this.groupingKey = groupingKey;
    }
}
