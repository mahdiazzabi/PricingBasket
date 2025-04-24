package com.pricing.basket.domain.model;


import java.math.BigDecimal;
import java.util.List;

public class Product {

    private String name;
    private BigDecimal price;
    private List<String> tags;

    public Product() {} // Requis pour Jackson

    public Product(String name, BigDecimal price, List<String> tags) {
        this.name = name;
        this.price = price;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<String> getTags() {
        return tags;
    }
}
