package com.fngry.monk.common.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * for springboot
 */
@Configuration
public class AutoConfiguration {

    @Bean
    public LogBeanPostProcessor logDigest() {
        return new LogBeanPostProcessor(new DigestInterceptor());
    }

}
