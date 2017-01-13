package com.fngry.monk.entity.test;

import lombok.Data;

/**
 * Created by gaorongyu on 16/12/14.
 */
@Data
public class OrderInfo extends IndexDbModel{

    private Integer id;

    private String isDeleted;

    private Integer creator;

    private java.util.Date gmtCreate;

    private Integer modifier;

    private java.util.Date gmtModified;

    private String orderSn;

    /**外部商家订单编码**/
    private String externalOrderSn;

    /**订单来源**/
    private Integer orderSourceType;

    /**销售员id**/
    private Integer saleId;

    /**销售员名称**/
    private String saleName;

    /**用户中心shop表的user_id,原来的名字user_id**/
    private Integer shopId;

    /**订单状态 -5 被拆单 -3 被合单 -2 删除 0 未确认 1 已确认 2 取消 3 无效**/
    private Integer orderStatus;

    /**0 未发货 1 已发货**/
    private Integer shippingStatus;

    /**付款状态 0 未付款 1 付款中 2 已付款**/
    private Integer payStatus;

    /**shop的门店名称**/
    private String companyName;

    /**收货人名称**/
    private String consignee;

    /**收货地址－乡村**/
    private Integer country;

    /**收货地址－省**/
    private Integer province;

    /**收货地址－城市**/
    private Integer city;

    /**收货地址－区**/
    private Integer district;

    /**收货地址－街道**/
    private Integer street;

    /**收货地址－详细信息**/
    private String address;

    /**收货人手机号码**/
    private String mobile;

    /**订单备注 内部备注,例如小秘书、销售备注**/
    private String postscript;

    /**发货方式**/
    private Integer shippingId;

    /**发货方式名称**/
    private String shippingName;

    /**支付方式 暂时交易中心定义**/
    private Integer payId;

    /**支付名称**/
    private String payName;

    /**商品金额**/
    private java.math.BigDecimal goodsAmount;

    /**运费**/
    private java.math.BigDecimal shippingFee;

    /**线上支付的实际金额**/
    private java.math.BigDecimal payFee;

    /****/
    private java.math.BigDecimal moneyPaid;

    /**红包优惠**/
    private java.math.BigDecimal bonus;

    /**订单金额**/
    private java.math.BigDecimal orderAmount;

    /**真实价格**/
    private java.math.BigDecimal oriOrderAmount;

    /**审计用订单金额**/
    private java.math.BigDecimal auditAmount;

    /**订单来源 云修|app**/
    private String referer;

    /**下单时间**/
    private java.util.Date addTime;

    /**订单确认下推时间**/
    private java.util.Date confirmTime;

    /**订单支付时间**/
    private java.util.Date payTime;

    /**订单发货时间**/
    private java.util.Date shippingTime;

    /**发票类型**/
    private String invType;

    /**税 暂时无用**/
    private java.math.BigDecimal tax;

    /**拆单合单使用父订单编号**/
    private Integer parentId;

    /**订单总折扣**/
    private java.math.BigDecimal discount;

    /**发货仓库id**/
    private Integer warehouseId;

    /**业务备注 比如业务经理手机号, 拆单合单优惠等,下单界面填的备注**/
    private String businessNote;

    /**属性扩展字段**/
    private String attributes;

    /**交易状态, 参考订单中心OperateKeyEnum**/
    private String tradeStatus;

    /**商家id**/
    private Integer sellerId;

    /**商家昵称**/
    private String sellerNick;

    /**用户类型：1门店、2普通用户**/
    private Integer userType;

    /**订单标记，通过“,”分隔（BHD被合单、BCD被拆单、GJG改价格、GPS改配送、DYD第一单、XSYTH销售已提货）**/
    private String orderFlags;

    /**支付商返回的流水号**/
    private String payNo;

    /**扫描支付url**/
    private String payUrl;

    /**城市站ID**/
    private Integer cityId;

    /**uc里面的登陆操作帐户id**/
    private Integer accountId;

    /**服务订单id**/
    private Integer orderServiceId;

    /**计划状态 1-可出库, 2-部分缺货, 3-全部缺货, 4-可调拨**/
    private Integer planStatus;

    /**运算优先级,数量大优先级高**/
    private Integer planPriority;

    /**拒收金额**/
    private java.math.BigDecimal returnAmount;

    /**取消状态 先申请取消 通过后变成取消确认**/
    private String warehouseStatus;

    /**出库单号**/
    private String deliveryNo;

    /**缺货标示 0:可预约 1：缺货**/
    private Integer stockoutType;

    /**下推出库单状态 0-未下推, 1－已下推**/
    private Integer transferStatus;

    /**到货确认状态 0 未到货，1 部分到货，2 全部到货**/
    private Integer arrivalStatus;

    /**发货日期**/
    private java.util.Date sendDate;

    /**交货日期 包括拒收时间**/
    private java.util.Date signDate;

    /**打印出库单时间**/
    private java.util.Date printTime;

    /**下推ERP时间**/
    private java.util.Date pushdownTime;

    /**出库时间**/
    private java.util.Date outWareTime;

    /**最后一次派送时间**/
    private java.util.Date redispatchTime;

    /**退货入库日期**/
    private java.util.Date returnInWareDate;

    /**收款日期**/
    private java.util.Date receiveCashDate;

    /**退款日期**/
    private java.util.Date refundDate;

    /**核销时间**/
    private java.util.Date gmtWriteOff;

    /**核销金额**/
    private java.math.BigDecimal writeOffAmount;

    /**实收总金额**/
    private java.math.BigDecimal receiveAmount;

    /**实退金额**/
    private java.math.BigDecimal realReturnAmount;

    /**核销状态:1,已核销,0,未核销**/
    private Integer writeOffStatus;

    /**财务收款方式(备注 1:现金收款,2:POS收款,3:银行转账,4:支付宝转账,5:在线收款（预付）)**/
    private Integer receiveType;

    /**打印状态 未打印  `已打印 错误列表**/
    private Integer printStatus;

    /**预约时间**/
    private java.util.Date appointmentTime;

    /**可出库时间**/
    private java.util.Date canOutWarehouseTime;

    @Override
    public String getPrimaryKey() {
        return this.id.toString();
    }
}
