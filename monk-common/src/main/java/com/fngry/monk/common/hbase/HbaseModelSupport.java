package com.fngry.monk.common.hbase;

import com.fngry.monk.common.hbase.rowkey.RowKey;
import com.fngry.monk.common.hbase.util.ByteUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.JSONBean;
import org.apache.hadoop.hbase.util.JsonMapper;

import java.util.Collection;

public abstract class HbaseModelSupport implements HbaseModel {

    private byte[] rowkey;

    @Override
    public byte[] resolveRowkey() {
        if (rowkey != null) {
            return rowkey;
        }

        try {
            rowkey = generateRowkey();
        } catch (Exception e) {
            // todo encapsulate RowKeyException
            throw new RuntimeException("resolve rowKey fail " + this.getClass(), e);
        }

        return rowkey;
    }

    private byte[] generateRowkey() throws Exception {
        Class<? extends RowKey> clazz = getRowKeyClass();
        RowKey rowKey = clazz.newInstance();
        rowKey.set(this);
        return rowKey.resolve();
    }

    @Override
    public void write(Put put) throws Exception {
        HColumnFamilySupport columnFamily = HColumnFamilySupport.get(this.getClass());

        byte[] family = columnFamily.name();

        Collection<HColumnSupport> columns = HColumnSupport.get(this.getClass());
        for (HColumnSupport columnSupport : columns) {
            Object value = FieldUtils.readField(columnSupport.getField(), this, true);
            if (value == null) {
                continue;
            }

            put.addColumn(family, columnSupport.name(), ByteUtil.toBytes(value));
        }

    }

    @Override
    public void read(Result result) throws Exception {
        HColumnFamilySupport columnFamily = HColumnFamilySupport.get(this.getClass());

        byte[] family = columnFamily.name();
        Collection<HColumnSupport> columns = HColumnSupport.get(this.getClass());

        for (HColumnSupport column : columns) {
            if (!result.containsColumn(family, column.name())) {
                continue;
            }

            byte[] value = result.getValue(family, column.name());
            if (value == null) {
                continue;
            }

            Object object = ByteUtil.fromBytes(value, column.getField().getType());
            FieldUtils.writeField(column.getField(), this, object, true);
        }

    }

    protected abstract Class<? extends RowKey> getRowKeyClass();

}
