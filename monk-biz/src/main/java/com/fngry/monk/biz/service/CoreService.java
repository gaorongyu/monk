package com.fngry.monk.biz.service;

import com.fngry.monk.common.result.Result;
import com.fngry.monk.dao.common.BaseQueryParam;
import com.fngry.monk.dao.common.PageQueryParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by gaorongyu on 16/12/1.
 */
public interface CoreService {

    public <T> Result<Integer> save(T value);

    public Result<Boolean> delete(Integer id);

    public <B> Result<Page<B>> query(PageQueryParam queryParam);

    public <T> Result<T> getById(Integer id);

    public <T> Result<List<T>> query(BaseQueryParam queryParam);

    public <T> Result<T> getByNumber(String number);

}
