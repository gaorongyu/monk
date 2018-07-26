package com.fngry.monk.common.hbase.impl;

import com.fngry.monk.common.hbase.HColumnFamilySupport;
import com.fngry.monk.common.hbase.HbaseClient;
import com.fngry.monk.common.hbase.HbaseModel;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

import java.io.IOException;
import java.util.List;

public class HBaseClientImpl implements HbaseClient {

    private static final int RETRY_TIMES = 3;

    private Configuration conf;

    private Connection connection;

    public HBaseClientImpl() {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "slave01.xxx.com,master01.xxx.com,master02.xxx.com");
        conf.set("zookeeper.znode.parent","/hbase-unsecure");

        try {
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] upsert(HbaseModel model) {
        byte[] rowKey = model.resolveRowkey();
        HColumnFamilySupport columnFamily = HColumnFamilySupport.get(model.getClass());

        TableName tableName = TableName.valueOf(columnFamily.get().table());
        HTable table = null;

        int tryTimes = 0;
        while (tryTimes < RETRY_TIMES) {
            try {
                table = (HTable) connection.getTable(tableName);
                Put put = new Put(rowKey);
                model.write(put);

                table.put(put);
                return rowKey;
            } catch (Exception e) {
                tryTimes ++;
                if (tryTimes == RETRY_TIMES) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    @Override
    public <T extends HbaseModel> T select(Class<?> clazz, byte[] rowKey) {
        return null;
    }

    @Override
    public byte[] delete(Class<? extends HbaseModel> clazz, byte[] rowKey) {
        return new byte[0];
    }

    @Override
    public <T extends HbaseModel> List<T> scan(Class<T> clazz, byte[] startRow, byte[] endRow) {
        return null;
    }

    @Override
    public <T extends HbaseModel> List<T> scan(Class<T> clazz, byte[] startRow, byte[] endRow, int limit) {
        return null;
    }

}
