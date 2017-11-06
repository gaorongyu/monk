package com.fngry.monk.common.sdk;

/**
 * Created by gaorongyu on 2017/11/6.
 */
public interface IPluginManager {

    void initPlugin(String beanName, Object bean) throws Exception;

    void postInitPlugin() throws Exception;

}
