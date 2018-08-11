package com.fngry.monk.common.log;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Digest {

    /**
     *
     * @return Logger name
     */
    String name();

    /**
     * parameter names
     * @return
     */
    String[] args() default {};

    /**
     * log template
     * {"SaleOrder", "update", "${resp.succeeded}", "${args0.orderId}", "${arg1.userId}"}
     * @return
     */
    String[] valueTemplate();

    /**
     * for async invoke resource
     * @return
     */
    boolean async() default false;

}
