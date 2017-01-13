package com.fngry.monk.dao.test;

import com.fngry.monk.dao.common.MyBatisRepository;

/**
 * Created by gaorongyu on 16/11/24.
 */
@MyBatisRepository
public interface TestMapper {

    public Integer selectCount();

}
