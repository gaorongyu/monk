package com.fngry.monk.common.hbase;

import com.fngry.monk.common.hbase.annotation.HColumn;
import com.fngry.monk.common.hbase.annotation.HColumnFamily;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HColumnFamilySupport {

    private static final Map<Class<? extends HbaseModel>, HColumnFamilySupport> COLUMNS_FAMILIES = new ConcurrentHashMap<>();

    private byte[] name;

    private HColumnFamily columnFamily;

    private HColumnFamilySupport(HColumnFamily columnFamily) {
        this.columnFamily = columnFamily;
        this.name = Bytes.toBytes(columnFamily.value());
    }

    public static HColumnFamilySupport get(Class<? extends HbaseModel> clazz) {
        HColumnFamilySupport result = COLUMNS_FAMILIES.get(clazz);

        if (result != null) {
            return result;
        }

        HColumnFamily annotation = clazz.getAnnotation(HColumnFamily.class);
        result = new HColumnFamilySupport(annotation);

        COLUMNS_FAMILIES.put(clazz, result);
        return result;
    }

    public byte[] name() {
        return this.name;
    }

    public HColumnFamily get() {
        return this.columnFamily;
    }


}
