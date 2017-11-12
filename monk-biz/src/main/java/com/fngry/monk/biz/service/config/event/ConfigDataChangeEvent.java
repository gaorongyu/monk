package com.fngry.monk.biz.service.config.event;

/**
 * 配置变更事件 通知集群其他机器更新配置
 *
 * Created by gaorongyu on 2017/11/12.
 */
public class ConfigDataChangeEvent extends Event {

    private String groupKey;

    private long changeTime;

    public ConfigDataChangeEvent() {
        super(null);
    }

    public ConfigDataChangeEvent(Object source) {
        super(source);
    }

    public ConfigDataChangeEvent(Object source, String groupKey, long changeTime) {
        super(source);
        this.groupKey = groupKey;
        this.changeTime = changeTime;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

}
