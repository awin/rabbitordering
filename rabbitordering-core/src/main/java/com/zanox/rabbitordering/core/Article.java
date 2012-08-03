package com.zanox.rabbitordering.core;

/**
 * @author christian.bick
 */
public class Article {

    public Article(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String id;
    public String name;
    public double price;
}
