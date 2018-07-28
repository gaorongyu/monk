package com.fngry.monk.biz.hbase.processor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MonkModelProcessor {

    Class<? extends ModelProcessor> target();

}
