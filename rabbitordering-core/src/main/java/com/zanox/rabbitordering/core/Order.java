package com.zanox.rabbitordering.core;

import java.util.Date;
import java.util.UUID;

/**
 * @author christian.bick
 */
public class Order {

    public enum State {
        ORDERED, PAYED, DELIVERED
    }

    public Order(Customer customer, Article article, int amount) {
        this.id = UUID.randomUUID().toString();
        this.orderDate = new Date();
        this.state = State.ORDERED;
        this.article = article;
        this.customer = customer;
        this.amount = amount;
    }

    public String id;
    public Article article;
    public Customer customer;
    public int amount;
    public Date orderDate;
    public Date deliveryDate;
    public State state;
}
