package com.fngry.monk.biz.test;

import com.fngry.monk.common.sdk.BizPlugin;
import com.fngry.monk.common.sdk.PluginManager;
import com.fngry.monk.dao.test.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaorongyu on 16/11/24.
 */
@BizPlugin(bizType = "TEST", opType = "READ_XML")
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private PluginManager pluginManager;

    @Autowired
    private TestMapper testMapper;

    @Override
    public Object test() {
        this.getClass().getResource("biz-aop.xml");
        return testMapper.selectCount();
    }

    @Override
    public String getPlugin(String bizType, String opType) {
        Object bean = pluginManager.getPlugin(bizType, opType);
        return bean.getClass().getName();
    }

}
