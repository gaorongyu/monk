package com.fngry.monk.biz.service.accounting.trf.config;

public class TrfJobConfig {

    private String jobId;

    private TrfConfig trfConfig;

    /**
     * 账期
     */
    private String glDate;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public TrfConfig getTrfConfig() {
        return trfConfig;
    }

    public void setTrfConfig(TrfConfig trfConfig) {
        this.trfConfig = trfConfig;
    }

    public String getGlDate() {
        return glDate;
    }

    public void setGlDate(String glDate) {
        this.glDate = glDate;
    }
}
