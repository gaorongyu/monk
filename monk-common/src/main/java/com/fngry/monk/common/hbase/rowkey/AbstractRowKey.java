package com.fngry.monk.common.hbase.rowkey;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class AbstractRowKey implements RowKey {


    private RowKeyComponent[] COMPONENTS;

    private int length;

    public AbstractRowKey(RowKeyComponent[] components) {
        this.COMPONENTS = components;
        this.length = Arrays.stream(COMPONENTS).map(e -> e.length()).reduce((a, b) -> a + b).get();
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public void set(Object object) {
        Field[] fields = this.getClass().getFields();

        for (Field field : fields) {

            try {
                Object value = RowKeyUtil.read(object, field.getName());
                if (value == null) {
                    continue;
                }

                FieldUtils.writeField(field, this, value, true);

            } catch (IllegalAccessException e) {
                // todo
                throw new RuntimeException("set rowkey fail", e);
            }

        }

    }

    @Override
    public byte[] resolve() {
        return resolve(COMPONENTS.length);
    }

    @Override
    public byte[] resolve(int components) {
        return resolve(components, (byte) 0);
    }

    @Override
    public byte[] resolve(int components, byte padding) {
        byte[] result = new byte[length];
        int index = resolve(result, components);

        if (padding == 0) {
            return result;
        }
        for (int i = index; i < result.length; i++) {
            result[i] = padding;
        }

        return result;
    }

    private int resolve(byte[] result, int components) {
        if (components > COMPONENTS.length) {
            throw new IllegalArgumentException("");
        }
        int index  = 0;

        for (int i = 0; i < components; i++) {
            RowKeyComponent component = COMPONENTS[i];
            try {
                component.write(this, result, index);
                index = index + component.length();
            } catch (Exception e) {
                throw new RuntimeException("fail to resolve " + component.name(), e);
            }
        }
        return index;
    }

    @Override
    public Pair<byte[], byte[]> resolveScan(int components) {
        byte[] start = new byte[this.length];
        byte[] end = new byte[this.length];

        int len = resolve(start, components);

        System.arraycopy(start, 0, end, 0, len);
        for (int i = len; i < end.length; i++) {
            end[i] = (byte) 0XFF;
        }

        return Pair.of(start, end);
    }

}
