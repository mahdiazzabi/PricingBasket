package com.pricing.basket.domain.model;

import java.math.BigDecimal;
import java.util.*;

public class Basket {
    private final List<Product> products;
    private List<DiscountEligibility> discounts;
    private BigDecimal subtotal;
    private BigDecimal total;

    public Basket(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public long countProduct(String productName) {
        return products.stream().filter(i -> i.getName().equalsIgnoreCase(productName)).count();
    }

    public List<DiscountEligibility> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountEligibility> discounts) {
        this.discounts = discounts;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
