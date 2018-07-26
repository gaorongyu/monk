package com.fngry.monk.common.hbase.rowkey;

import org.apache.hadoop.hbase.util.Bytes;

public class PlainComponent implements RowKeyComponent {

    private final String name;

    private final String fieldName;

    private final int pos;

    private final int length;

    public PlainComponent(String name, String fieldName, int pos, int length) {
        this.name = name;
        this.fieldName = fieldName;
        this.pos = pos;
        this.length = length;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void write(RowKey rowKey, byte[] bytes, int index) throws Exception {
        String value = (String) RowKeyUtil.read(rowKey, this.fieldName);
        RowKeyUtil.write(Bytes.toBytes(value), pos, length, bytes, index);
    }

}
