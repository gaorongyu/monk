package com.fngry.monk.common.hbase.rowkey;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Map;

public class RowKeyUtil {

    public static Object[] read(Object target, String... fieldNames) throws IllegalAccessException {
        Object[] result = new Object[fieldNames.length];

        for (int i = 0; i < result.length; i++) {
            read(target, fieldNames[i]);
        }
        return result;
    }

    public static Object read(Object target, String fieldName) throws IllegalAccessException {
        if (target instanceof Map) {
            return ((Map) target).get(fieldName);
        } else {
            return FieldUtils.readDeclaredField(target, fieldName);
        }
    }

    public static void write(byte[] value, int length, byte[] bytes, int index) {
        System.arraycopy(value, 0, bytes, index, length);
    }

    public static void write(byte[] value, int pos, int length, byte[] bytes, int index) {
        System.arraycopy(value, pos, bytes, index, Math.min(length, value.length - pos));
    }

}
