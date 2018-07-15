package com.fngry.monk.common.extpoint.platform;

import com.fngry.monk.common.extpoint.core.ExtensionFacade;
import com.fngry.monk.common.extpoint.platform.delivery.DeliveryExtension;
import com.fngry.monk.common.extpoint.platform.promotion.PromotionExtension;

public abstract class TradeExtensionFacade implements ExtensionFacade {

    public abstract DeliveryExtension getDeliveryExtension();

    public abstract PromotionExtension getPromotionExtension();

}
