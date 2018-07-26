package com.fngry.monk.common.hbase;

import com.fngry.monk.common.hbase.annotation.HColumn;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HColumnSupport {

    private static final Map<Class<? extends HbaseModel>, Collection<HColumnSupport>> COLUMNS = new ConcurrentHashMap<>();

    private byte[] name;

    private Field field;

    private HColumnSupport(Field field) {
        this.field = field;
        this.name = Bytes.toBytes(field.getAnnotation(HColumn.class).value());
    }

    public static Collection<HColumnSupport> get(Class<? extends HbaseModel> clazz) {
        Collection<HColumnSupport> result = COLUMNS.get(clazz);

        if (result != null) {
            return result;
        }

        result = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            HColumn annotation = field.getAnnotation(HColumn.class);
            if (annotation == null) {
                continue;
            }
            result.add(new HColumnSupport(field));
        }

        COLUMNS.put(clazz, result);
        return result;
    }

    public Field getField() {
        return this.field;
    }

    public byte[] name() {
        return this.name;
    }

}
