package com.fngry.monk.dao.common;

import lombok.Data;

/**
 * Created by gaorongyu on 16/12/5.
 */
@Data
public class PageQueryParam extends BaseQueryParam {

    private int limit;

    private int offset;

}
