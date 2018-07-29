package com.fngry.monk.biz.service.accounting.common.config;

import com.fngry.monk.biz.service.accounting.common.filter.FilterEnum;

import java.io.Serializable;
import java.util.Map;

public interface FilterRule extends Serializable {

    FilterEnum getFilter();

    Map<String, String> getParams();

}
