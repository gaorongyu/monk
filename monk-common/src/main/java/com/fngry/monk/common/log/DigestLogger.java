package com.fngry.monk.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.util.Map;

public class  DigestLogger {

    private static final String SEPARATOR = ",";

    private final Logger logger;

    private final String[] template;

    public DigestLogger(Logger logger, String[] template) {
        this.logger = logger;
        this.template = template;
    }

    /**
     * need app set logger with digest.name in logback.xml
     *
     * @param name
     * @param value
     * @return
     */
    public static DigestLogger newLogger(String name, String... value) {
        Logger logger = LoggerFactory.getLogger("digest." + name);

        if (value == null || value.length == 0) {
            return new DigestLogger(logger, null);
        }

        return new DigestLogger(logger, value);
    }

    private static String createTemplate(String[] value) {
        return null;
    }

    public void log(Map<String, Object> context, boolean succeeded, long startTime, long endTime) {
        logger.info(renderTemplate(context, succeeded, startTime, endTime));
    }

    private String renderTemplate(Map<String, Object> context, boolean succeeded, long startTime, long endTime) {
        StringBuffer logContent = new StringBuffer();

        logContent.append(succeeded ? "Y" : "N").append(SEPARATOR);
        logContent.append(startTime).append(SEPARATOR);
        logContent.append(endTime).append(SEPARATOR);

        for (String valueExp : template) {
            logContent.append(TemplateUtil.render(valueExp, context)).append(SEPARATOR);
        }
        return logContent.toString();
    }


}
