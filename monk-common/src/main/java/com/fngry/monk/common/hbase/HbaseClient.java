package com.fngry.monk.common.hbase;

import java.util.List;

public interface HbaseClient {

    byte[] upsert(HbaseModel model);

    <T extends HbaseModel> T select(Class<?> clazz, byte[] rowKey);

    byte[] delete(Class<? extends HbaseModel> clazz, byte[] rowKey);

    <T extends HbaseModel> List<T> scan(Class<T> clazz, byte[] startRow, byte[] endRow);

    <T extends HbaseModel> List<T> scan(Class<T> clazz, byte[] startRow, byte[] endRow, int limit);

}
