package com.pricing.basket.model;


import java.math.BigDecimal;

public class Product {

    private String name;
    private BigDecimal price;

    public Product() {} // Requis pour Jackson

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
