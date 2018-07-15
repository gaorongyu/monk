package com.fngry.monk.common.extpoint.platform.promotion;

import com.fngry.monk.common.extpoint.core.ExtensionPoints;
import com.fngry.monk.common.extpoint.platform.OrderItem;

import java.math.BigDecimal;

public interface PromotionExtension extends ExtensionPoints {

    BigDecimal getDiscount(OrderItem orderItem);

}
