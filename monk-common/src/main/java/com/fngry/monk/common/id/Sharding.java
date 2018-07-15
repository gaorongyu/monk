package com.fngry.monk.common.id;

import java.util.HashMap;
import java.util.Map;

public class Sharding {

    private final String version;

    private final Map<ModelCode, Integer> tableCount;

    public static final Sharding V_01 = new Sharding("01");

    static {
        V_01.tableCount.put(ModelCode.SALE_ORDER, 1024);
    }

    private Sharding(String version) {
        this(version, new HashMap<ModelCode, Integer>());
    }

    private Sharding(String version, Map<ModelCode, Integer> tableCount) {
        this.version = version;
        this.tableCount = tableCount;
    }

    /**
     * 获取分表的个数
     * @return
     */
    public int getTableCount(ModelCode modelCode) {
        return 1024;
    }

    public int hashExternalId(ModelCode modelCodeEnum, Long userId) {
        Long hash = userId & Integer.MAX_VALUE;
        hash = hash % getTableCount(modelCodeEnum);
        return hash.intValue();
    }

}
