package com.zanox.rabbitordering.client;

import com.zanox.rabbiteasy.testing.BrokerSetup;
import com.zanox.rabbitordering.core.Article;
import com.zanox.rabbitordering.core.Customer;
import com.zanox.rabbitordering.core.Order;
import com.zanox.rabbitordering.core.ShopPersistence;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author christian.bick
 */
public class OrderProcessConsole {

    private static ShopPersistence persistence;
    private static OrderProcess orderProcess;
    private static BrokerSetup brokerSetup = new BrokerSetup();

    static void initBroker() throws IOException {
        String exchange = "com.zanox.rabbitordering";

        brokerSetup.declareExchange(exchange, "topic");
        brokerSetup.declareAndBindQueue(
                exchange + ".order.created-statistics", exchange, exchange + ".order.created");
        brokerSetup.declareAndBindQueue(
                exchange + ".order.payed-statistics", exchange, exchange + ".order.payed");
        brokerSetup.declareAndBindQueue(
                exchange + ".order.delivered-statistics", exchange, exchange + ".order.delivered");
        System.out.println("Broker set up");
    }

    static void tearDownBroker() throws IOException {
        brokerSetup.tearDown();
    }

    static void initContext() throws IOException {
        WeldContainer container = new Weld().initialize();
        container.instance().select(OrderProcessEventBinder.class).get().initialize();
        orderProcess = container.instance().select(OrderProcess.class).get();
        persistence = container.instance().select(ShopPersistence.class).get();
        System.out.println("Context initialized");
    }

    static void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("-------------------------------------------");
        System.out.println("Test Customers");
        System.out.println("-------------------------------------------");
        for (Customer customer : persistence.getCustomers()) {
            System.out.println(customer.username + ": " + customer.firstName + " " + customer.lastName);
        }
        System.out.println("-------------------------------------------");
        System.out.println("Test Articles");
        System.out.println("-------------------------------------------");
        for (Article article : persistence.getArticles()) {
            System.out.println(article.id + ": " + article.name);
        }
        System.out.println("-------------------------------------------");
        System.out.println("Commands");
        System.out.println("-------------------------------------------");
        System.out.println("create <username> <articleId> <amount>");
        System.out.println("pay <orderId> <payment>");
        System.out.println("deliver <orderId>");
        System.out.println("-------------------------------------------");

        while (true) {
            try {
                String command = in.next();
                if (command.equalsIgnoreCase("exit")) {
                    break;
                } else if (command.equalsIgnoreCase("create")) {
                    String username = in.next();
                    String article = in.next();
                    int amount = in.nextInt();
                    Order order = orderProcess.create(username, article, amount);
                    System.out.println("Order " + order.id + " of " + order.amount +  " " + order.article.name + "(s) for " + order.customer.firstName + " " + order.customer.lastName + " created ");
                } else if (command.equals("pay")) {
                    String orderId = in.next();
                    double payment = in.nextFloat();
                    boolean payed = orderProcess.pay(orderId, payment);
                    if (payed) {
                        System.out.println("Order " + orderId + " was payed");
                    } else {
                        System.out.println("Order " + orderId + " was not payed (no full amount)");
                    }
                } else if (command.equals("deliver")) {
                    String orderId = in.next();
                    orderProcess.deliver(orderId);
                    System.out.println("Order " + orderId + " delivered");
                } else {
                    System.out.println("Unknown command");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        in.close();
    }

    public static void main(String args[]) throws IOException {
        initBroker();
        initContext();
        run();
        tearDownBroker();
    }
}
