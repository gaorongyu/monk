package com.fngry.monk.common.extpoint.platform.delivery;

import com.fngry.monk.common.extpoint.core.ExtensionPoints;
import com.fngry.monk.common.extpoint.platform.OrderItem;

public interface DeliveryExtension extends ExtensionPoints {

    String getTransportMethod(OrderItem orderItem);

    String getReceiveGoodsMethod(OrderItem orderItem);

}
