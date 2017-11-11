package com.fngry.monk.common.jmx;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * MBean operation annotation
 * Created by gaorongyu on 2017/11/7.
 */
@Target({ METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JmxOperation {

}
