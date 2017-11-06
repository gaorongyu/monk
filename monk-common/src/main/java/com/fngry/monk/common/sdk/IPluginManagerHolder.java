package com.fngry.monk.common.sdk;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 插件容器 getBean()统一入口
 * Created by gaorongyu on 2017/11/6.
 */
public class IPluginManagerHolder implements ApplicationContextAware, InitializingBean {

    private List<IPluginManager> pluginManagerList;

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        String[] beanNames = this.applicationContext.getBeanDefinitionNames();

        // for loop init plugins
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);

            for (IPluginManager pluginManager : pluginManagerList) {
                pluginManager.initPlugin(beanName, bean);
            }
        }
        // post init plugin
        for (IPluginManager pluginManager : pluginManagerList) {
            pluginManager.postInitPlugin();
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<IPluginManager> getPluginManagerList() {
        return pluginManagerList;
    }

    public void setPluginManagerList(List<IPluginManager> pluginManagerList) {
        this.pluginManagerList = pluginManagerList;
    }

}
