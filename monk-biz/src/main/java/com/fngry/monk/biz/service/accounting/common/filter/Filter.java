package com.fngry.monk.biz.service.accounting.common.filter;

import java.util.Map;

/**
 * filter
 * @param
 * @author gaorongyu
 */
public interface Filter {

    /**
     *
     * @param params param context
     * @param target target Object
     * @return target Object matches or not filter item
     */
    boolean matches(Map<String, Object> params, Object target);

}
