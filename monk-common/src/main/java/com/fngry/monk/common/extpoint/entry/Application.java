package com.fngry.monk.common.extpoint.entry;

import com.fngry.monk.common.extpoint.core.ExtensionInvoker;
import com.fngry.monk.common.extpoint.core.ExtensionMappingBuilder;
import com.fngry.monk.common.extpoint.core.ExtensionPoints;
import com.fngry.monk.common.extpoint.platform.OrderItem;
import com.fngry.monk.common.extpoint.platform.delivery.DeliveryExtension;
import com.fngry.monk.common.extpoint.platform.promotion.PromotionExtension;
import com.fngry.monk.common.extpoint.plugins.tmall.TmallTradeExtensionFacade;

import java.math.BigDecimal;

public class Application {



    private void run() {
        ExtensionMappingBuilder.getInstance().build();

        OrderItem orderItem = new OrderItem();
        orderItem.setBizCode("tmall");

        ExtensionInvoker<DeliveryExtension> invoker = new ExtensionInvoker<>(DeliveryExtension.class);
        String transportMethod = invoker.execute(orderItem, p -> p.getTransportMethod(orderItem));

        ExtensionInvoker<PromotionExtension> invoker2 = new ExtensionInvoker<>(PromotionExtension.class);
        BigDecimal discount = invoker2.execute(orderItem, p -> p.getDiscount(orderItem));

        System.out.println(transportMethod);
        System.out.println(discount);

    }


    private void runWithFacade() {
        ExtensionMappingBuilder.getInstance().build();

        OrderItem orderItem = new OrderItem();
        orderItem.setBizCode("tmall");

        ExtensionInvoker<DeliveryExtension> invoker = new ExtensionInvoker<>(DeliveryExtension.class);
        TmallTradeExtensionFacade facade = new TmallTradeExtensionFacade();
        DeliveryExtension deliveryExtension = (DeliveryExtension) facade.getExtensionPointsByCode("tmallDelivery");

        String transportMethod = deliveryExtension.getTransportMethod(orderItem);
        System.out.println(transportMethod);

    }

}
