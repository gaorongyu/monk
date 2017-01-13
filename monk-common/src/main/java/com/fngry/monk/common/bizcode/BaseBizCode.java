/*
 * @BaseBizCode.java 16/11/30.
 *
 * Copyright (c) 2014-2016 fngry.
 */
package com.fngry.monk.common.bizcode;

/**
 * 业务码基类
 *
 * @author gaorongyu
 * @version 1.0 16/3/25.
 */
public class BaseBizCode implements IBizCode {

    private static final long serialVersionUID = -8262752060333871434L;

	/**
     * 业务码
     */
    private String code;

    /**
     * 信息
     */
    private String message;

    protected BaseBizCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return message != null
                ? String.format(this.message, args) : "";
    }

}
