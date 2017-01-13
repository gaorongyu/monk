package com.fngry.monk.dao.test;

import com.fngry.monk.dao.common.MyBatisRepository;
import com.fngry.monk.entity.test.OrderInfo;

import java.util.List;

/**
 * Created by gaorongyu on 16/12/14.
 */
@MyBatisRepository
public interface OrderInfoMapper {

    public List<OrderInfo> select(OrderInfoQueryParam param);

}
