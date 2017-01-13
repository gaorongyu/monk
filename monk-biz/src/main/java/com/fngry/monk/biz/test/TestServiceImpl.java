package com.fngry.monk.biz.test;

import com.fngry.monk.dao.test.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaorongyu on 16/11/24.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public Object test() {
        this.getClass().getResource("biz-aop.xml");
        return testMapper.selectCount();
    }

}
