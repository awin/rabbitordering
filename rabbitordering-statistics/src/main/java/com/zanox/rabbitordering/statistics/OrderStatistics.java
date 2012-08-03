package com.zanox.rabbitordering.statistics;

import com.zanox.rabbitordering.core.OrderCreatedEvent;
import com.zanox.rabbitordering.core.OrderDeliveredEvent;
import com.zanox.rabbitordering.core.OrderPayedEvent;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

/**
 * @author christian.bick
 */
@Singleton
public class OrderStatistics {

    private volatile int waitingForPayment;
    private volatile int waitingForShipment;
    private volatile int shippedOrders;

    public void onOrderCreated(@Observes OrderCreatedEvent event) {
        waitingForPayment++;
    }

    public void onOrderPayed(@Observes OrderPayedEvent event) {
        waitingForPayment--;
        waitingForShipment++;
    }

    public void onOrderDelivered(@Observes OrderDeliveredEvent event) {
        waitingForShipment--;
        shippedOrders++;
    }

    public int getWaitingForPayment() {
        return waitingForPayment;
    }

    public int getWaitingForShipment() {
        return waitingForShipment;
    }

    public int getShippedOrders() {
        return shippedOrders;
    }

    public int getTotalOrders() {
        return waitingForPayment + waitingForShipment + shippedOrders;
    }

    public void print() {
        print("Total orders", getTotalOrders());
        print("Unpayed orders", getWaitingForPayment());
        print("Undelivered orders", getWaitingForShipment());
        print("Delivered orders", getShippedOrders());
    }

    void print(String name, int value) {
        System.out.println(name + ": " + value);
    }
}
