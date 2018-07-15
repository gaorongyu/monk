package com.fngry.monk.common.extpoint.plugins.tmall;

import com.fngry.monk.common.extpoint.platform.OrderItem;
import com.fngry.monk.common.extpoint.platform.delivery.DeliveryExtension;

public class TmallDeliveryExtension implements DeliveryExtension {

    @Override
    public String getTransportMethod(OrderItem orderItem) {
        return "oneDayExpress";
    }

    @Override
    public String getReceiveGoodsMethod(OrderItem orderItem) {
        return "receiveAtHome";
    }

}
