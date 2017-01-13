package com.fngry.monk.biz.service;

import com.fngry.monk.common.result.Result;
import com.fngry.monk.dao.common.BaseQueryParam;
import com.fngry.monk.dao.common.CoreBaseMapper;
import com.fngry.monk.dao.common.PageQueryParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by gaorongyu on 16/12/1.
 */
public abstract class CoreServiceImpl implements CoreService {

    /**
     * 返回具体实体数据库查询接口
     * @return CoreBaseMapper
     */
    protected abstract CoreBaseMapper getCoreBaseMapper();

    /**
     * 返回具体对象查询参数
     * @return BaseQueryParam
     */
    protected abstract BaseQueryParam getPageQueryParam();

    @Override
    public <T> Result<Integer> save(T value) {
        return null;
    }

    @Override
    public Result<Boolean> delete(Integer id) {
        return null;
    }

    @Override
    public <B> Result<Page<B>> query(PageQueryParam queryParam) {
        return null;
    }

    @Override
    public <T> Result<T> getById(Integer id) {
        return null;
    }

    @Override
    public <T> Result<List<T>> query(BaseQueryParam queryParam) {
        return null;
    }

    @Override
    public <T> Result<T> getByNumber(String number) {
        return null;
    }

}
