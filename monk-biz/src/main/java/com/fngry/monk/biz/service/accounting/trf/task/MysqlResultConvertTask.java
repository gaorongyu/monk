package com.fngry.monk.biz.service.accounting.trf.task;

import com.fngry.monk.biz.service.accounting.common.BizEntity;
import org.apache.spark.api.java.function.Function;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class MysqlResultConvertTask implements Function<ResultSet, BizEntity> {

    private static final long serialVersionUID = 1593947617791761455L;

    @Override
    public BizEntity call(ResultSet resultSet) throws Exception {
        BizEntity entity = new BizEntity();

        Map<String, Object> data = new HashMap<>();
        int fieldCount = resultSet.getMetaData().getColumnCount();
        for (int i = 0; i < fieldCount; i++ ) {
            data.put(resultSet.getMetaData().getColumnName(i + 1), resultSet.getObject(i + 1));
        }
        entity.setData(data);

        return entity;
    }

}
