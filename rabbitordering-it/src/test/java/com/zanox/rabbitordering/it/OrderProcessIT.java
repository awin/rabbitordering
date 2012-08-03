package com.zanox.rabbitordering.it;

import com.zanox.rabbitordering.client.OrderProcess;
import com.zanox.rabbitordering.core.Order;
import com.zanox.rabbitordering.core.ShopPersistence;
import com.zanox.rabbitordering.statistics.OrderStatistics;
import junit.framework.Assert;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Before;
import org.junit.Test;

/**
 * @author christian.bick
 */
public class OrderProcessIT {

    ShopPersistence persistence;
    OrderStatistics orderStatistics;
    OrderProcess orderProcess;

    @Before
    public void before() {
        WeldContainer container = new Weld().initialize();
        persistence = container.instance().select(ShopPersistence.class).get();
        orderStatistics = container.instance().select(OrderStatistics.class).get();
        orderProcess = container.instance().select(OrderProcess.class).get();
    }

    @Test
    public void testOrderLifecycle() {
        Order order = orderProcess.create("markz", "1", 2);
        Assert.assertEquals(1, orderStatistics.getWaitingForPayment());
        Assert.assertEquals(0, orderStatistics.getWaitingForShipment());
        Assert.assertEquals(0, orderStatistics.getShippedOrders());
        Assert.assertEquals(1, orderStatistics.getTotalOrders());

        boolean payed = orderProcess.pay(order.id, order.article.price * order.amount);
        Assert.assertTrue(payed);
        Assert.assertEquals(0, orderStatistics.getWaitingForPayment());
        Assert.assertEquals(1, orderStatistics.getWaitingForShipment());
        Assert.assertEquals(0, orderStatistics.getShippedOrders());
        Assert.assertEquals(1, orderStatistics.getTotalOrders());

        orderProcess.deliver(order.id);
        Assert.assertEquals(0, orderStatistics.getWaitingForPayment());
        Assert.assertEquals(0, orderStatistics.getWaitingForShipment());
        Assert.assertEquals(1, orderStatistics.getShippedOrders());
        Assert.assertEquals(1, orderStatistics.getTotalOrders());
    }

}
