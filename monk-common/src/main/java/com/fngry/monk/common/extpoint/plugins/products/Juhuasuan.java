package com.fngry.monk.common.extpoint.plugins.products;

import com.fngry.monk.common.extpoint.core.ProductCode;
import com.fngry.monk.common.extpoint.platform.OrderItem;
import com.fngry.monk.common.extpoint.platform.TradeExtensionFacade;
import com.fngry.monk.common.extpoint.platform.delivery.DeliveryExtension;
import com.fngry.monk.common.extpoint.platform.promotion.PromotionExtension;

import java.math.BigDecimal;

@ProductCode("juhuasuan")
public class Juhuasuan extends TradeExtensionFacade {
    @Override
    public DeliveryExtension getDeliveryExtension() {
        return null;
    }

    @Override
    public PromotionExtension getPromotionExtension() {
        return new PromotionExtension() {
            @Override
            public BigDecimal getDiscount(OrderItem orderItem) {
                return new BigDecimal("35");
            }
        };
    }
}
