package com.fngry.monk.common.extpoint.entry;

import com.fngry.monk.common.extpoint.platform.OrderItem;
import com.fngry.monk.common.extpoint.platform.delivery.DeliveryExtension;

public class Entrance {

    private DeliveryExtension deliveryExtension;

    public void run() {
        OrderItem orderItem = new OrderItem();

        String transportMethod = deliveryExtension.getTransportMethod(orderItem);
        String receiveGoodsMethod = deliveryExtension.getReceiveGoodsMethod(orderItem);

        System.out.println(transportMethod);
        System.out.println(receiveGoodsMethod);
    }


}
