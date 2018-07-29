package com.fngry.monk.biz.service.accounting.vld.validator;

import com.fngry.monk.biz.service.accounting.common.filter.Filter;
import com.fngry.monk.biz.service.accounting.common.filter.ScriptFilter;

public enum ValidatorEnum {

    SCRIPT_FILTER(ScriptValidator.class, "groovy脚本校验器");

    ValidatorEnum(Class<? extends Validator> validatorClass, String description) {
        this.validatorClass = validatorClass;
        this.description = description;
    }

    Class<? extends Validator> validatorClass;

    String description;

    public Class<? extends Validator> getValidatorClass() {
        return validatorClass;
    }

    public void setValidatorClass(Class<? extends Validator> validatorClass) {
        this.validatorClass = validatorClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
