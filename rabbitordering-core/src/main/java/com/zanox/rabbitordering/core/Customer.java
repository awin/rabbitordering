package com.zanox.rabbitordering.core;

/**
 * @author christian.bick
 */
public class Customer {

    public Customer(String username, String firstName, String lastName, String address, String postCode, String city) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postCode = postCode;
        this.city = city;
    }

    public String username;
    public String firstName;
    public String lastName;
    public String address;
    public String postCode;
    public String city;
}
