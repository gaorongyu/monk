package com.fngry.monk.biz.util;

import com.fngry.monk.entity.test.IndexDbModel;
import org.elasticsearch.common.Strings;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaorongyu on 16/12/18.
 */
public class IndexUtil {

    public static Map<String, Object> makeIndexDoc(boolean isUnderLine,
            String primaryKey, IndexDbModel entity) {
        Map<String, Object> doc = new HashMap<>();

        doc.put(primaryKey, entity.getPrimaryKey());

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            String fieldName = field.getName();
            String key = isUnderLine
                    ? Strings.toUnderscoreCase(field.getName())
                    : field.getName();

            String getterName = createGetterName(fieldName);
            try {
                Method getter = entity.getClass().getMethod(getterName);
                if (Map.class.isAssignableFrom(getter.getReturnType())) {
                    makeIndexOfMap(entity, doc, getter);
                } else {
                    doc.put(key, getter.invoke(entity));
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return doc;
    }

    private static void makeIndexOfMap(IndexDbModel entity, Map<String, Object> doc, Method getter)
            throws IllegalAccessException, InvocationTargetException {
        Map<String, Object> mapValue = (Map<String, Object>) getter.invoke(entity);
        for (Map.Entry<String, Object> entry : mapValue.entrySet()) {
            doc.put(entry.getKey().toString(), entry.getValue());
        }
    }

    public static String createGetterName(String fieldName) {
        StringBuffer methodName = new StringBuffer();
        methodName.append("get");
        methodName.append(fieldName.substring(0, 1).toUpperCase());
        methodName.append(fieldName.substring(1));

        return methodName.toString();
    }

}
