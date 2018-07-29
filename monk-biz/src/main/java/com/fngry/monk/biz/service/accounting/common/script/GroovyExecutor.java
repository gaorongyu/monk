package com.fngry.monk.biz.service.accounting.common.script;

import com.fngry.monk.biz.service.accounting.common.Constants;
import com.fngry.monk.biz.service.accounting.common.filter.ScriptFilter;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * groovy script executor
 * @author gaorongyu
 */
public class GroovyExecutor {


    protected GroovyShell groovyShell;

    protected Script script;

    /**
     * groovy script
     */
    protected String expression;

    public GroovyExecutor(Map<String, Object> params) {
        this.expression = (String) params.get(Constants.EXPRESSION);
        Assert.notNull(expression, "expression can not be null!");
        groovyShell = new GroovyShell(ScriptFilter.class.getClassLoader());
        script = groovyShell.parse(expression);
    }


    public <T> T execute(Map params, Object target) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.TARGET, target);

        Binding binding = new Binding(map);

        Script targetScript;
        try {
            targetScript = InvokerHelper.createScript(this.script.getClass(), binding);
        } catch (Exception e) {
            throw new RuntimeException(String.format("fail to create groovy script: %s", expression));
        }

        return (T) targetScript.run();
    }

}
