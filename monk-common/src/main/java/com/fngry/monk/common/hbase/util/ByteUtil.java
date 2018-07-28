package com.fngry.monk.common.hbase.util;

import com.alibaba.fastjson.JSON;
import org.apache.hadoop.hbase.util.Bytes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ByteUtil {

    private static Map<Class<?>, Function<Object, byte[]>> OBJECT_TO_BYTE_FUNCTIONS = new HashMap<>();

    private static Map<Class<?>, Function<byte[], Object>> BYTE_TO_OBJECT_FUNCTIONS = new HashMap<>();

    static {
        // todo
        OBJECT_TO_BYTE_FUNCTIONS.put(int.class, e -> Bytes.toBytes((int) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(long.class, e -> Bytes.toBytes((long) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(double.class, e -> Bytes.toBytes((double) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(float.class, e -> Bytes.toBytes((float) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(char.class, e -> Bytes.toBytes((char) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(boolean.class, e -> Bytes.toBytes((boolean) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(byte[].class, e -> (byte[]) e);
        OBJECT_TO_BYTE_FUNCTIONS.put(Integer.class, e -> Bytes.toBytes((int) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(Long.class, e -> Bytes.toBytes((long) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(Double.class, e -> Bytes.toBytes((double) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(Float.class, e -> Bytes.toBytes((float) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(Character.class, e -> Bytes.toBytes((char) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(Boolean.class, e -> Bytes.toBytes((boolean) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(Byte[].class, e -> (byte[]) e);
        OBJECT_TO_BYTE_FUNCTIONS.put(String.class, e -> Bytes.toBytes((String) e));
        OBJECT_TO_BYTE_FUNCTIONS.put(Date.class, e -> Bytes.toBytes(((Date) e).getTime()));
        OBJECT_TO_BYTE_FUNCTIONS.put(BigDecimal.class, e -> Bytes.toBytes(((BigDecimal) e).toPlainString()));

        BYTE_TO_OBJECT_FUNCTIONS.put(int.class, e -> Bytes.toInt(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(long.class, e -> Bytes.toLong(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(double.class, e -> Bytes.toDouble(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(float.class, e -> Bytes.toFloat(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(char.class, e -> (char) Bytes.toInt(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(boolean.class, e -> Bytes.toBoolean(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(byte[].class, e -> e);
        BYTE_TO_OBJECT_FUNCTIONS.put(Integer.class, e -> Bytes.toInt(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(Long.class, e -> Bytes.toLong(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(Double.class, e -> Bytes.toDouble(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(Float.class, e -> Bytes.toFloat(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(Character.class, e -> (char) Bytes.toInt(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(Boolean.class, e -> Bytes.toBoolean(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(Byte[].class, e -> e);
        BYTE_TO_OBJECT_FUNCTIONS.put(String.class, e -> Bytes.toString(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(Date.class, e -> new Date(Bytes.toLong(e)));
        BYTE_TO_OBJECT_FUNCTIONS.put(BigDecimal.class, e -> new BigDecimal(Bytes.toString(e)));
    }

    private static Function<Object, byte[]> getToByteFunction(Class<?> clazz) {
        Function<Object, byte[]> convertFunction = OBJECT_TO_BYTE_FUNCTIONS.get(clazz);
        if (convertFunction != null) {
            return convertFunction;
        }

        Optional<Function<Object, byte[]>> func = OBJECT_TO_BYTE_FUNCTIONS.entrySet().stream()
                .filter(e -> e.getClass().isAssignableFrom(clazz))
                .map(e -> e.getValue())
                .findFirst();

        return func.orElse(null);
    }

    private static Function<byte[], Object> getToObjectFunction(Class<?> clazz) {
        Function<byte[], Object> convertFunction = BYTE_TO_OBJECT_FUNCTIONS.get(clazz);
        if (convertFunction != null) {
            return convertFunction;
        }

        Optional<Function<byte[], Object>> func = BYTE_TO_OBJECT_FUNCTIONS.entrySet().stream()
                .filter(e -> e.getClass().isAssignableFrom(clazz))
                .map(e -> e.getValue())
                .findFirst();

        return func.orElse(null);
    }


    public static final byte[] toBytes(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object can not be null");
        }
        Function<Object, byte[]> convertFunction = getToByteFunction(object.getClass());
        if (convertFunction != null) {
            return convertFunction.apply(object);
        }
        return Bytes.toBytes(JSON.toJSONString(object));
    }

    public static final <T> T fromBytes(byte[] bytes, Class<T> clazz) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes can not be null");
        }
        if (clazz == null) {
            throw new IllegalArgumentException("clazz can not be null");
        }
        Function<byte[], Object> convertFunction = getToObjectFunction(clazz);
        if (convertFunction != null) {
            return (T) convertFunction.apply(bytes);
        }

        return (T) JSON.parseObject(bytes, clazz);
    }

}
