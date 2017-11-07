package com.fngry.monk.common.sdk;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.fngry.monk.common.util.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

/**
 * Created by gaorongyu on 2017/5/14.
 */
@Repository("bizPluginManager")
public class BizPluginManager implements IPluginManager, ApplicationContextAware {

    private static final String ENHANCER_BY_CGLIB = "EnhancerBySpringCGLIB";

    private ApplicationContext applicationContext = null;

    private Map<String, String> pluginContext = new HashMap<>();

    public <T> T getPlugin(String bizType, String opType) {
        String identity = getPluginIdentity(bizType, opType);
        String beanName = pluginContext.get(identity);

        if (StringUtil.isBlank(beanName)) {
            throw new RuntimeException(" no plugin defined, bizType: " + bizType
                    + " opType: " + opType);
        }
        return (T) applicationContext.getBean(beanName);
    }

    @PostConstruct
    public void init() {
        System.out.println("init");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initPlugin(String beanName, Object bean) throws Exception {
        Class<?> clazz = bean.getClass();

        if (clazz.getName().contains(ENHANCER_BY_CGLIB)) {
            clazz = clazz.getSuperclass();
        }
        if (isBizPlugin(clazz)) {
            initBizPlugin(beanName, clazz);
        }
    }

    @Override
    public void postInitPlugin() throws Exception {
        // after init plugin operation eg. subscribe mq message

    }

    private boolean isBizPlugin(Class clazz) {
        return clazz.isAnnotationPresent(BizPlugin.class);
    }

    private String getPluginIdentity(BizPlugin bizPlugin) {
        return getPluginIdentity(bizPlugin.bizType(), bizPlugin.opType());
    }

    private String getPluginIdentity(String bizType, String opType) {
        return bizType + "$$" + opType;
    }

    private void initBizPlugin(String beanName, Class<?> clazz) {
        BizPlugin bizPlugin = clazz.getAnnotation(BizPlugin.class);
        String identity = getPluginIdentity(bizPlugin);

        if (StringUtil.isBlank(pluginContext.get(identity))) {
            pluginContext.put(identity, beanName);
        }
    }

}
