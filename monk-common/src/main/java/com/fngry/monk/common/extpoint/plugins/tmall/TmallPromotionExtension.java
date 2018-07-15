package com.fngry.monk.common.extpoint.plugins.tmall;

import com.fngry.monk.common.extpoint.platform.OrderItem;
import com.fngry.monk.common.extpoint.platform.promotion.PromotionExtension;

import java.math.BigDecimal;

public class TmallPromotionExtension implements PromotionExtension {
    @Override
    public BigDecimal getDiscount(OrderItem orderItem) {
        return new BigDecimal("15");
    }
}
