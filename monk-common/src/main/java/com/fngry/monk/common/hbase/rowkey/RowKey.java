package com.fngry.monk.common.hbase.rowkey;

import org.apache.commons.lang3.tuple.Pair;

public interface RowKey {

    int length();

    void set(Object object);

    byte[] resolve();

    byte[] resolve(int components);

    byte[] resolve(int components, byte padding);

    Pair<byte[], byte[]> resolveScan(int components);

}
