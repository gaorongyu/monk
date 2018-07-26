package com.fngry.monk.common.hbase;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

public interface HbaseModel {

    byte[] resolveRowkey();

    void write(Put put) throws Exception;

    void read(Result result) throws Exception;

}
