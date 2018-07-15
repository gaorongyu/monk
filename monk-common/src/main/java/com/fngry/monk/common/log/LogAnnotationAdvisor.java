package com.fngry.monk.common.log;

import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;

public class LogAnnotationAdvisor extends AbstractPointcutAdvisor {

    private Pointcut pointcut;

    private LogInterceptor logInterceptor;

    public LogAnnotationAdvisor(LogInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
        this.pointcut = new LogPointCut(logInterceptor);
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.logInterceptor;
    }

    private static class LogPointCut implements Pointcut {

        private final ClassFilter classFilter =  new AnnotationClassFilter(LogAutoProxy.class, true);

        private final MethodMatcher methodMatcher;


        public LogPointCut(LogInterceptor logInterceptor) {
            methodMatcher = new AnnotationMethodMatcher(logInterceptor.annotationClass);
        }

        @Override
        public ClassFilter getClassFilter() {
            return null;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return null;
        }
    }

}
