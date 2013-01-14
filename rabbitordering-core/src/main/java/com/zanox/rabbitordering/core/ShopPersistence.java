package com.zanox.rabbitordering.core;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author christian.bick
 */
@Singleton
public class ShopPersistence {

    Map<String, Order> orders = new ConcurrentHashMap<String, Order>();
    Map<String, Article> articles = new ConcurrentHashMap<String, Article>();
    Map<String, Customer> customers = new ConcurrentHashMap<String, Customer>();

    public ShopPersistence() {
        articles.put("gloves", new Article("gloves", "Gloves", 2.23));
        articles.put("shirt", new Article("shirt", "Shirt", 4.57));
        articles.put("pents", new Article("pents", "Pents", 7.12));
        customers.put("markz", new Customer("markz", "Mark", "Zuckerberg", "Facebook Street 5", "12345", "San Francisco"));
        customers.put("stephj", new Customer("stephj", "Steph", "Jobs", "Apple Street 10", "54321", "Berlin"));
        customers.put("billg", new Customer("billg", "Bill", "Gates", "Microsoft Street 1", "6789", "Paris"));
    }

    public Collection<Order> getOrders() {
        return orders.values();
    }

    public Collection<Article> getArticles() {
        return articles.values();
    }

    public Collection<Customer> getCustomers() {
        return customers.values();
    }

    public void createOrder(Order order) {
        orders.put(order.id, order);
    }

    public Order getOrder(String id) {
        return orders.get(id);
    }

    public Article getArticle(String id) {
        return articles.get(id);
    }

    public Customer getCustomer(String username) {
        return customers.get(username);
    }
}
