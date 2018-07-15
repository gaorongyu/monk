
package com.fngry.monk.common.log;

import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;

public class LogBeanPostProcessor extends AbstractAdvisingBeanPostProcessor {

    public LogBeanPostProcessor(LogInterceptor logInterceptor) {
        this.advisor = new LogAnnotationAdvisor(logInterceptor);
        this.beforeExistingAdvisors = true;
        setProxyTargetClass(true);
    }

}
