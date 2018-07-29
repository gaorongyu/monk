package com.fngry.monk.biz.hbase.model;

import com.fngry.monk.biz.hbase.processor.MonkModelProcessor;
import com.fngry.monk.biz.hbase.processor.impl.BizOrderProcessor;
import com.fngry.monk.common.hbase.annotation.HColumn;
import com.fngry.monk.common.hbase.annotation.HColumnFamily;

import java.math.BigDecimal;

@HColumnFamily(table = "monk_biz_order", value = "f")
@MonkModelProcessor(target = BizOrderProcessor.class)
public class BizOrder extends MonkModelSupport {

    private static final long serialVersionUID = 4198305932452388632L;

    @HColumn("jobId")
    private String jobId;

    @HColumn("orderId")
    private String orderId;

    @HColumn("sellerId")
    private String sellerId;

    @HColumn("sellerNick")
    private String sellerNick;

    @HColumn("amount")
    private BigDecimal amount;


    @HColumn("bizName")
    private String bizName;

    @HColumn("source")
    private String source;

    @HColumn("orderNo")
    private String orderNo;

    @HColumn("parentSource")
    private String parentSource;

    @HColumn("parentOrderNo")
    private String parentOrderNo;

    @HColumn("parentOrderRowKey")
    private byte[] parentOrderRowKey;

    @HColumn("remark")
    private String remark;

    @Override
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getParentSource() {
        return parentSource;
    }

    public void setParentSource(String parentSource) {
        this.parentSource = parentSource;
    }

    public String getParentOrderNo() {
        return parentOrderNo;
    }

    public void setParentOrderNo(String parentOrderNo) {
        this.parentOrderNo = parentOrderNo;
    }

    public byte[] getParentOrderRowKey() {
        return parentOrderRowKey;
    }

    public void setParentOrderRowKey(byte[] parentOrderRowKey) {
        this.parentOrderRowKey = parentOrderRowKey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
