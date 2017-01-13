package com.fngry.monk.dao.common;

import java.util.List;

/**
 *
 * 数据库操作接口
 *
 * Created by gaorongyu on 16/12/1.
 *
 */
public interface CoreBaseMapper {

    public <T extends BaseQueryParam> Integer selectCount(T params);

    public <T, S extends BaseQueryParam> List<T> select(S params);

    public <T> void insertSelective(T value);

    public <T> void insert(T value);

    public <T> T selectByPrimaryKey(Integer id);

    public <T> int updateByPrimaryKeySelective(T value);

}
