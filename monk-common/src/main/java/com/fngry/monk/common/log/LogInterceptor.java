package com.fngry.monk.common.log;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class LogInterceptor<T extends Annotation> implements MethodInterceptor {

    protected final Class<T> annotationClass;

    public LogInterceptor(Class<T> clazz) {
        this.annotationClass = clazz;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Method method = methodInvocation.getMethod();
        Class clazz = methodInvocation.getThis().getClass();

        Method specificMethod = AopUtils.getMostSpecificMethod(method, clazz);

        T annotation = specificMethod.getAnnotation(annotationClass);
        if (annotation == null) {
            return methodInvocation.proceed();
        }

        return this.invoke(methodInvocation, specificMethod, annotation);
    }

    /**
     * do log
     * @param methodInvocation
     * @param specificMethod
     * @param annotation
     * @return
     */
    protected abstract Object invoke(MethodInvocation methodInvocation, Method specificMethod, T annotation);

}
