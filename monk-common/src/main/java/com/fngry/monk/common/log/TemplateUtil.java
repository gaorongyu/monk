package com.fngry.monk.common.log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * template util
 *  other realization such as groovy-all
 *  avoid import third party jar when use this log function, we realize use native jdk
 *
 * @author gaorongyu
 *
 */
public class TemplateUtil {

    /**
     * matches ${arg10.attribute}, ${arg1.method()}
     */
    private static Pattern ARG_PATTERN = Pattern.compile("\\$\\{[^\\f\\n\\r\\t\\v\\{\\}]+\\}");

    public static String render(String valueExp, Map<String, Object> context) {
        String valueResolve = valueExp;
        Matcher matcher = ARG_PATTERN.matcher(valueExp);
        while (matcher.find()) {
            String matchPart = matcher.group();
            String expression = matchPart.substring(2, matchPart.length() - 1);
            Object value = eval(expression, context);
            valueResolve = valueResolve.replace(matchPart, value != null ? value.toString() : "null");
        }
        return valueResolve;
    }

    private static Object eval(String expression, Map<String, Object> context) {
        String[] exps = expression.split("\\.");
        Object target = null;

        for (int i = 0; i < exps.length; i++) {
            String exp = exps[i];
            if (i == 0) {
                target = context.get(exp);
                continue;
            }

            if (target == null) {
                return null;
            }
            try {
                if (isMethodInvoke(exp)) {
                    Method method = target.getClass().getDeclaredMethod(parseMethodName(exp));
                    target = method.invoke(target);
                } else {
                    if (Map.class.isAssignableFrom(target.getClass())) {
                        target = ((Map) target).get(exp);
                    } else {
                        Field field = target.getClass().getDeclaredField(exp);
                        field.setAccessible(true);
                        target = field.get(target);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("eval fail for " + exp, e);
            }
        }
        return target;
    }

    private static final String parseMethodName(String exp) {
        return exp.replaceAll("\\(\\)", "");
    }

    private static boolean isMethodInvoke(String exp) {
        return exp.indexOf("(") > 0;
    }


    // for test
    public static void main(String[] args) throws Exception {
        Map<String, Object>  context = new HashMap<>();
        context.put("arg0", "123");

        Map arg1 = new HashMap();
        arg1.put("orderNo", "456");
        context.put("arg1", arg1);

        System.out.println(render("${arg0}", context));
        System.out.println(render("${arg1.orderNo}", context));
    }

}
