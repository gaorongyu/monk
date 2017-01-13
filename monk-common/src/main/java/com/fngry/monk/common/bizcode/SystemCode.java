package com.fngry.monk.common.bizcode;

/**
 * Created by gaorongyu on 16/11/30.
 */
public class SystemCode extends BaseBizCode {


    public static final SystemCode SYSTEM_ERROR =
            new SystemCode("010101", "系统异常");


    public SystemCode(String code, String message) {
        super(code, message);
    }

}
