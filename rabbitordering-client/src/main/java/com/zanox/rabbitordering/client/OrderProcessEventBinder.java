package com.zanox.rabbitordering.client;

import com.zanox.rabbiteasy.cdi.EventBinder;
import com.zanox.rabbitordering.core.OrderCreatedEvent;
import com.zanox.rabbitordering.core.OrderDeliveredEvent;
import com.zanox.rabbitordering.core.OrderPayedEvent;

/**
 * @author christian.bick
 */
public class OrderProcessEventBinder extends EventBinder {

    @Override
    protected void bindEvents() {
        bind(OrderCreatedEvent.class).toExchange("com.zanox.rabbitordering")
                .withRoutingKey("com.zanox.rabbitordering.order.created")
                .withPersistentMessages()
                .withPublisherConfirms();

        bind(OrderPayedEvent.class).toExchange("com.zanox.rabbitordering")
                .withRoutingKey("com.zanox.rabbitordering.order.payed")
                .withPersistentMessages()
                .withPublisherConfirms();

        bind(OrderDeliveredEvent.class).toExchange("com.zanox.rabbitordering")
                .withRoutingKey("com.zanox.rabbitordering.order.delivered")
                .withPersistentMessages()
                .withPublisherConfirms();
    }
}
