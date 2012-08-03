package com.zanox.rabbitordering.client;

import com.zanox.rabbitordering.core.*;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

/**
 * @author christian.bick
 */
@Singleton
public class OrderProcess {

    @Inject
    private ShopPersistence persistence;

    @Inject
    private OrderCreatedEvent orderCreatedEvent;

    @Inject
    private OrderPayedEvent orderPayedEvent;

    @Inject
    private OrderDeliveredEvent orderDeliveredEvent;

    @Inject
    private Event<OrderCreatedEvent> orderCreatedEventControl;

    @Inject
    private Event<OrderPayedEvent> orderPayedEventControl;

    @Inject
    private Event<OrderDeliveredEvent> orderDeliveredEventControl;

    public Order create(String username, String articleId, int amount) {
        Article article = persistence.getArticle(articleId);
        Customer customer = persistence.getCustomer(username);
        Order order = new Order(customer, article, amount);
        persistence.createOrder(order);
        orderCreatedEvent.setId(order.id);
        orderCreatedEventControl.fire(orderCreatedEvent);
        return order;
    }

    public boolean pay(String orderId, double payment) {
        Order order = persistence.getOrder(orderId);
        double expectedPayment = order.article.price * order.amount;
        if (payment >= expectedPayment) {
            order.state = Order.State.PAYED;
            orderPayedEvent.setId(order.id);
            orderPayedEventControl.fire(orderPayedEvent);
            return true;
        }
        return false;
    }

    public void deliver(String orderId) {
        Order order = persistence.getOrder(orderId);
        order.deliveryDate = new Date();
        order.state = Order.State.DELIVERED;
        orderDeliveredEvent.setId(order.id);
        orderDeliveredEventControl.fire(orderDeliveredEvent);
    }

}
