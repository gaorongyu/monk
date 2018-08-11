package com.fngry.monk.common.log;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DigestInterceptor extends LogInterceptor<Digest>{


    public DigestInterceptor() {
        super(Digest.class);
    }

    @Override
    protected Object invoke(MethodInvocation methodInvocation, Method specificMethod, Digest annotation) {
        long startTime = System.currentTimeMillis();
        Object result;

        CompletableFuture<Object> cf = null;
        try {
            result = methodInvocation.proceed();
            cf = CompletableFuture.completedFuture(result);
        } catch (Throwable e) {
            cf.completeExceptionally(e);
        } finally {
            long endTime = System.currentTimeMillis();
            if (annotation.async()) {
                // async execute digest log
                cf.handleAsync((s, t) -> {
                    doLog(annotation, methodInvocation, s, t, startTime, endTime);
                    return s;
                });
            } else {
                cf.handle((s, t) -> {
                    doLog(annotation, methodInvocation, s, t, startTime, endTime);
                    return s;
                });
            }
        }
        return cf.join();
    }

    private void doLog(Digest annotation, MethodInvocation methodInvocation, Object result,
                       Throwable throwable, long startTime, long endTime) {
        Map<String ,Object> context = new HashMap<>();
        context.put("response", result);
        context.put("error", throwable);

        Object[] arguments = methodInvocation.getArguments();
        for( int i = 0; i < annotation.args().length; i++) {
            context.put(annotation.args()[i], arguments[i]);
        }

        DigestLogger digestLogger = DigestLoggerCache.INSTANCE.getLogger(annotation);
        digestLogger.log(context, throwable == null, startTime, endTime);

    }

}
