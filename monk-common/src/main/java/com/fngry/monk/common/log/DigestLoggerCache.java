package com.fngry.monk.common.log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DigestLoggerCache {

    public static final DigestLoggerCache INSTANCE = new DigestLoggerCache();

    private Map<Digest, DigestLogger> cache = new ConcurrentHashMap<>();

    private DigestLoggerCache() {

    }


    public DigestLogger getLogger(Digest digest) {

        DigestLogger logger = cache.get(digest);
        if (logger != null) {
            return logger;
        }

        logger = DigestLogger.newLogger(digest.name(), digest.valueTemplate());
        cache.put(digest, logger);
        return logger;
    }

}
