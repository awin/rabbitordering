package com.zanox.rabbitordering.statistics;

import com.zanox.rabbiteasy.cdi.EventBinder;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author christian.bick
 */
public class OrderStatisticsConsole {

    private static OrderStatistics orderStatistics;

    static void initialize() throws IOException {
        WeldContainer container = new Weld().initialize();
        EventBinder eventBinder = container.instance().select(OrderStatisticsEventBinder.class).get();
        eventBinder.initialize();
        orderStatistics = container.instance().select(OrderStatistics.class).get();
    }

    static void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("-------------------------------------------");
        System.out.println("Commands");
        System.out.println("-------------------------------------------");
        System.out.println("print (prints the statistics)");
        System.out.println("exit (quits this application)");
        while (true) {
            try {
                String command = in.next();
                if (command.equalsIgnoreCase("exit")) {
                    break;
                } else if (command.equalsIgnoreCase("print")) {
                    orderStatistics.print();
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
        initialize();
        run();
    }

}
