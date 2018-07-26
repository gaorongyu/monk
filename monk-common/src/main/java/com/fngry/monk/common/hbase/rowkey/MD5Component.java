package com.fngry.monk.common.hbase.rowkey;

import org.apache.hadoop.hbase.util.Bytes;

import java.security.MessageDigest;

public class MD5Component implements RowKeyComponent {

    public static final int LENGTH = 16;

    private final String name;

    private final String[] fieldNames;

    public MD5Component(String name, String... fieldNames) {
        this.name = name;
        this.fieldNames = fieldNames;
    }

    @Override
    public int length() {
        return LENGTH;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void write(RowKey rowKey, byte[] bytes, int index) throws Exception {
        Object[] values = RowKeyUtil.read(rowKey, fieldNames);

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            if (value instanceof byte[]) {
                messageDigest.update((byte[]) value);
            } else {
                messageDigest.update(Bytes.toBytes((String) value));
            }
        }

        RowKeyUtil.write(messageDigest.digest(), LENGTH, bytes, index);
    }

}
