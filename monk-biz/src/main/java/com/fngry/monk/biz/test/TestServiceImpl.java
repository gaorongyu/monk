package com.fngry.monk.biz.test;

import com.fngry.monk.biz.service.config.event.LocalDataChangeEvent;
import com.fngry.monk.biz.service.config.event.EventDispatcher;
import com.fngry.monk.common.jmx.JmxOperation;
import com.fngry.monk.common.jmx.JmxResource;
import com.fngry.monk.common.sdk.BizPlugin;
import com.fngry.monk.common.sdk.BizPluginManager;
import com.fngry.monk.dao.test.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaorongyu on 16/11/24.
 */
@JmxResource
@BizPlugin(bizType = "TEST", opType = "READ_XML")
@Service("testService")
public class TestServiceImpl implements TestService {

    @Autowired
    private BizPluginManager bizPluginManager;

    @Autowired
    private TestMapper testMapper;

    @Override
    public Object test() {
        this.getClass().getResource("biz-aop.xml");
        return testMapper.selectCount();
    }

    @JmxOperation
    @Override
    public String getPlugin(String bizType, String opType) {
        Object bean = bizPluginManager.getPlugin(bizType, opType);
        return bean.getClass().getName();
    }

    @JmxOperation
    public void eventDispatch(String groupKey) {
        LocalDataChangeEvent event = new LocalDataChangeEvent(this, groupKey, System.currentTimeMillis());
        EventDispatcher.fireEvent(event);
    }

}
