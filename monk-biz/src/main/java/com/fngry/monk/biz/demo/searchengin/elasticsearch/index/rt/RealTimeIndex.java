package com.fngry.monk.biz.demo.searchengin.elasticsearch.index.rt;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.elasticsearch.common.collect.Tuple;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by gaorongyu on 17/1/10.
 */
public abstract class RealTimeIndex {

    @Value("${monk.canal.host}")
    private String canalHost;

    @Value("${monk.canal.port}")
    private String canalPort;

    protected volatile boolean isRunning;

    protected abstract void dealEntry(
            List<CanalEntry.Entry> entries, Tuple<String, String> target);





}
