package com.fngry.monk.common.extpoint.core;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TemplateExt {

    String[] codes() default "";

    String[] scenario() default "";

}
