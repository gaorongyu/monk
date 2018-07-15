package com.fngry.monk.common.extpoint.platform.promotion;

import com.fngry.monk.common.extpoint.core.ExtensionInvoker;
import com.fngry.monk.common.extpoint.platform.OrderItem;

import java.math.BigDecimal;

public class PromotionExtensionInvoker extends ExtensionInvoker<PromotionExtension> implements PromotionExtension {


    public PromotionExtensionInvoker() {
        super(PromotionExtension.class);
    }

    @Override
    public BigDecimal getDiscount(OrderItem orderItem) {
        // execute extend point
        return this.execute(orderItem, p -> p.getDiscount(orderItem));
    }

}
