package com.fngry.monk.biz.service.accounting.common.filter;

import com.fngry.monk.biz.service.accounting.common.script.GroovyExecutor;

import java.util.Map;

/**
 * groovy script filter
 * @author gaorongyu
 */
public class ScriptFilter extends GroovyExecutor implements Filter {

    public ScriptFilter(Map<String, Object> params) {
        super(params);
    }

    @Override
    public boolean matches(Map params, Object target) {
        return execute(params, target);
    }

}
