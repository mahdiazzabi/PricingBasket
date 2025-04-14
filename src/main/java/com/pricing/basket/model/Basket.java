package main.java.com.pricing.basket.model;

import java.math.BigDecimal;
import java.util.*;

public class Basket {
    private final List<Product> products;

    public Basket(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public long countProduct(String productName) {
        return products.stream().filter(i -> i.getName().equalsIgnoreCase(productName)).count();
    }

    public BigDecimal calculateSubtotal() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
