package com.fngry.monk.biz.service.accounting.vld.validator;

import java.util.Map;

/**
 * validator
 * @param
 * @author gaorongyu
 */
public interface Validator {

    /**
     *
     * @param params param context
     * @param target target Object
     * @return target Object valid or not
     */
    boolean validate(Map<String, Object> params, Object target);

}
