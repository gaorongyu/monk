package com.fngry.monk.common.extpoint.core;

public class BizInstanceId<T> {

    private T bizInstanceId;

    private String bizCode;

    protected BizInstanceId(String bizCode, T bizInstanceId) {
        this.bizCode = bizCode;
        this.bizInstanceId = bizInstanceId;
    }

    public static <T> BizInstanceId of(String bizCode, T bizInstanceId) {
        return new BizInstanceId(bizCode, bizInstanceId);
    }

    public T getBizInstanceId() {
        return bizInstanceId;
    }

    public void setBizInstanceId(T bizInstanceId) {
        this.bizInstanceId = bizInstanceId;
    }

    public String getBizCode() {
        return bizCode;
    }

}
