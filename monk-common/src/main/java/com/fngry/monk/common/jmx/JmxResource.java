package com.fngry.monk.common.jmx;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by gaorongyu on 2017/11/7.
 */
@Target({ TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JmxResource {

}
