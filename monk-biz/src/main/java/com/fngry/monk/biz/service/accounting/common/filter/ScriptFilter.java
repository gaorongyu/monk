package com.fngry.monk.biz.service.accounting.common.filter;

import com.fngry.monk.biz.service.accounting.common.Constants;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * groovy script filter
 * @author gaorongyu
 */
public class ScriptFilter implements Filter {

    private GroovyShell groovyShell;

    private Script script;

    /**
     * groovy script
     */
    private String expression;

    public ScriptFilter(Map<String, Object> params) {
        this.expression = (String) params.get(Constants.EXPRESSION);
        Assert.notNull(expression, "expression can not be null!");
        groovyShell = new GroovyShell(ScriptFilter.class.getClassLoader());
        script = groovyShell.parse(expression);
    }


    @Override
    public boolean matches(Map params, Object target) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.TARGET, target);

        Binding binding = new Binding(map);

        Script tagetScript;
        try {
            tagetScript = InvokerHelper.createScript(this.script.getClass(), binding);
        } catch (Exception e) {
            throw new RuntimeException(String.format("fail to create groovy script: %s", expression));
        }

        return (boolean) tagetScript.run();
    }

}
