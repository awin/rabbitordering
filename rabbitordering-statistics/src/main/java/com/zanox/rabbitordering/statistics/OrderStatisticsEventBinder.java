package com.zanox.rabbitordering.statistics;

import com.zanox.rabbiteasy.cdi.EventBinder;
import com.zanox.rabbitordering.core.OrderCreatedEvent;
import com.zanox.rabbitordering.core.OrderDeliveredEvent;
import com.zanox.rabbitordering.core.OrderPayedEvent;

/**
 * @author christian.bick
 */
public class OrderStatisticsEventBinder extends EventBinder {

    @Override
    protected void bindEvents() {
        bind(OrderCreatedEvent.class).toQueue("com.zanox.rabbitordering.order.created-statistics");
        bind(OrderPayedEvent.class).toQueue("com.zanox.rabbitordering.order.payed-statistics");
        bind(OrderDeliveredEvent.class).toQueue("com.zanox.rabbitordering.order.delivered-statistics");
    }
}
