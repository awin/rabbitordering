package com.zanox.rabbitordering.core;

import com.zanox.rabbiteasy.testing.BrokerSetup;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author christian.bick
 */
public class Setup {

    private static BrokerSetup brokerSetup = new BrokerSetup();

    public static void main(String args[]) throws IOException {
        String exchange = "com.zanox.rabbitordering";

        brokerSetup.declareExchange(exchange, "topic");
        brokerSetup.declareAndBindQueue(
                exchange + ".order.created-statistics", exchange, exchange + ".order.created");
        brokerSetup.declareAndBindQueue(
                exchange + ".order.payed-statistics", exchange, exchange + ".order.payed");
        brokerSetup.declareAndBindQueue(
                exchange + ".order.delivered-statistics", exchange, exchange + ".order.delivered");
        Scanner in = new Scanner(System.in);
        System.out.println("Broker set up");
        while (true) {
            String command = in.next();
            if (command.equalsIgnoreCase("exit")) {
                break;
            }
        }
        brokerSetup.tearDown();
        in.close();
    }

}
