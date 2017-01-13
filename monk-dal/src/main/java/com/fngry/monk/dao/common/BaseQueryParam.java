package com.fngry.monk.dao.common;

import lombok.Data;

import java.util.List;

/**
 *
 * 查询参数基类  提供分页参数, 具体实体分页查询参数继承该类
 *
 * Created by gaorongyu on 16/12/1.
 *
 */
@Data
public class BaseQueryParam {

    private List<String> sorts;

}
