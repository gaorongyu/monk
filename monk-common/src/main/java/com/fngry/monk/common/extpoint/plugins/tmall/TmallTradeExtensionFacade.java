package com.fngry.monk.common.extpoint.plugins.tmall;

import com.fngry.monk.common.extpoint.platform.OrderItem;
import com.fngry.monk.common.extpoint.platform.TradeExtensionFacade;
import com.fngry.monk.common.extpoint.platform.delivery.DeliveryExtension;
import com.fngry.monk.common.extpoint.platform.promotion.PromotionExtension;

import java.math.BigDecimal;

public class TmallTradeExtensionFacade extends TradeExtensionFacade {
    @Override
    public DeliveryExtension getDeliveryExtension() {
        return new DeliveryExtension() {
            @Override
            public String getTransportMethod(OrderItem orderItem) {
                return "tmall transport method";
            }

            @Override
            public String getReceiveGoodsMethod(OrderItem orderItem) {
                return "tmall receive goods method";
            }
        };
    }

    @Override
    public PromotionExtension getPromotionExtension() {
        return new PromotionExtension() {
            @Override
            public BigDecimal getDiscount(OrderItem orderItem) {
                return new BigDecimal("20");
            }
        };
    }

}
