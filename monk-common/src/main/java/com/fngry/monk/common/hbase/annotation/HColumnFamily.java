package com.fngry.monk.common.hbase.annotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HColumnFamily {

    String table() default "";

    String value() default "f";

}
