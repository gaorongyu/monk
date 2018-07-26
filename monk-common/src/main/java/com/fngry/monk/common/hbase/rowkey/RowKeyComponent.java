package com.fngry.monk.common.hbase.rowkey;

public interface RowKeyComponent<T extends RowKey> {

    int length();

    String name();

    void write(T rowKey, byte[] bytes, int index) throws Exception;

}
