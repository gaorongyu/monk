package com.fngry.monk.common.hbase.impl;

import com.fngry.monk.common.hbase.HColumnFamilySupport;
import com.fngry.monk.common.hbase.HbaseClient;
import com.fngry.monk.common.hbase.HbaseModel;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HBaseClientImpl implements HbaseClient {

    private static final int RETRY_TIMES = 3;

    private Configuration conf;

    private Connection connection;

    public HBaseClientImpl() {
        this("localhost:2181");
    }

    public HBaseClientImpl(String zkAddress) {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", zkAddress);
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        conf.set("zookeeper.znode.parent","/hbase-unsecure");

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

        int tryTimes = 0;
        while (tryTimes < RETRY_TIMES) {
            try {
                Table table = connection.getTable(tableName);
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
    public <T extends HbaseModel> T select(Class<T> clazz, byte[] rowKey) {
        HColumnFamilySupport columnFamily = HColumnFamilySupport.get(clazz);
        TableName tableName = TableName.valueOf(columnFamily.get().table());
        T model;

        try {
            model = clazz.newInstance();
            Table table = connection.getTable(tableName);

            Get get = new Get(rowKey);
            get.addFamily(columnFamily.name());

            Result result = table.get(get);
            if (result != null) {
                model.read(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return model;
    }

    @Override
    public <T extends HbaseModel> byte[] delete(Class<T> clazz, byte[] rowKey) {
        HColumnFamilySupport columnFamily = HColumnFamilySupport.get(clazz);
        TableName tableName = TableName.valueOf(columnFamily.get().table());

        try {
            Table table = connection.getTable(tableName);
            Delete delete = new Delete(rowKey);
            table.delete(delete);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rowKey;
    }

    @Override
    public <T extends HbaseModel> List<T> scan(Class<T> clazz, byte[] startRow, byte[] endRow) {
        return scan(clazz, startRow, endRow, -1);
    }

    @Override
    public <T extends HbaseModel> List<T>  scan(Class<T> clazz, byte[] startRow, byte[] endRow, int limit) {
        HColumnFamilySupport columnFamily = HColumnFamilySupport.get(clazz);
        TableName tableName = TableName.valueOf(columnFamily.get().table());

        List<T> list = new ArrayList<>();
        int cnt = 0;

        try {
            Scan scan = new Scan()
                    .withStartRow(startRow)
                    .withStopRow(endRow);
            scan.addFamily(columnFamily.name());

            Table table = connection.getTable(tableName);
            ResultScanner scanner = table.getScanner(scan);

            for (Result result : scanner) {
                T t = clazz.newInstance();
                t.read(result);
                list.add(t);

                if (limit != -1 && cnt >= limit) {
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

}
