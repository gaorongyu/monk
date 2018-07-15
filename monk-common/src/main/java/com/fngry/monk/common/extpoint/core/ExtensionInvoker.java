package com.fngry.monk.common.extpoint.core;

public class ExtensionInvoker<Ext> {

    private Class<Ext> extClass;

    public ExtensionInvoker(Class<Ext> extClass) {
        this.extClass = extClass;
    }

    public <T extends BizInstance, R> R execute(T target,
                                                ExtensionCallback<Ext, R> callback) {

        String bizCode = target.getBizCode();

        Ext point = ExtensionMappingBuilder
                .getInstance()
                .getExtPoint(this.extClass, bizCode);

        return callback.apply(point);

    }

}
