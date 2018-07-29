package com.fngry.monk.biz.service.accounting.trf.config;

import com.fngry.monk.biz.service.accounting.vld.config.ValidConfig;

import java.io.Serializable;
import java.util.List;

public class TrfConfig implements Serializable {

    private static final long serialVersionUID = 2246155846435634656L;

    private String configId;

    private String configName;

    private EntityConfig entityConfig;

    private List<ValidConfig> validConfigList;

    private List<TrfModelConfig> trfModelConfigList;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public EntityConfig getEntityConfig() {
        return entityConfig;
    }

    public void setEntityConfig(EntityConfig entityConfig) {
        this.entityConfig = entityConfig;
    }

    public List<ValidConfig> getValidConfigList() {
        return validConfigList;
    }

    public void setValidConfigList(List<ValidConfig> validConfigList) {
        this.validConfigList = validConfigList;
    }

    public List<TrfModelConfig> getTrfModelConfigList() {
        return trfModelConfigList;
    }

    public void setTrfModelConfigList(List<TrfModelConfig> trfModelConfigList) {
        this.trfModelConfigList = trfModelConfigList;
    }
}
