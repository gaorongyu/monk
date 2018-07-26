package com.fngry.monk.common.hbase.util;

import com.alibaba.fastjson.JSON;
import org.apache.hadoop.hbase.util.Bytes;

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
        OBJECT_TO_BYTE_FUNCTIONS.put(Date.class, e -> Bytes.toBytes(((Date) e).getTime()));

        BYTE_TO_OBJECT_FUNCTIONS.put(int.class, e -> Bytes.toInt(e));
        BYTE_TO_OBJECT_FUNCTIONS.put(Date.class, e -> new Date(Bytes.toLong(e)));

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
