package com.fngry.monk.biz.service.accounting.vld.config;

import java.io.Serializable;
import java.util.Map;

public class ValidatorRule implements Serializable {

    private static final long serialVersionUID = -3288879835577802346L;

    private String ruleId;

    private String ruleName;

    private String validator;

    private Map<String, Object> params;


    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }


    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
