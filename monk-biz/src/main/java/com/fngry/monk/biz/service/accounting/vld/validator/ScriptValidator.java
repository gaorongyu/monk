package com.fngry.monk.biz.service.accounting.vld.validator;

import com.fngry.monk.biz.service.accounting.common.script.GroovyExecutor;

import java.util.Map;

public class ScriptValidator extends GroovyExecutor implements Validator {

    public ScriptValidator(Map<String, Object> params) {
        super(params);
    }

    @Override
    public boolean validate(Map<String, Object> params, Object target) {
        return execute(params, target);
    }

}
