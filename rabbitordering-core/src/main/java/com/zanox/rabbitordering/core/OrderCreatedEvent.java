package com.zanox.rabbitordering.core;

import com.zanox.rabbiteasy.cdi.ContainsId;

/**
 * @author christian.bick
 */
public class OrderCreatedEvent implements ContainsId<String> {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
