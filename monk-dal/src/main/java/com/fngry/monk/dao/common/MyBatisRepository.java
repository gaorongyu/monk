package com.fngry.monk.dao.common;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by gaorongyu on 16/11/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MyBatisRepository {
    String value() default "";
}
