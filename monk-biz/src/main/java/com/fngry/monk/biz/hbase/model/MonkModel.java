package com.fngry.monk.biz.hbase.model;

import com.fngry.monk.common.hbase.HbaseModel;

import java.io.Serializable;

public interface MonkModel extends HbaseModel, Serializable {

    void setJobId(String jobId);

}
