package com.pricing.basket;

import com.pricing.basket.model.Basket;
import com.pricing.basket.model.Product;
import com.pricing.basket.service.PriceProductService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class PricingBasketApp {

    private final PriceProductService priceProductService;

    public PricingBasketApp(PriceProductService priceProductService) {
        this.priceProductService = priceProductService;
    }

    public static void main(String[] args){
        PriceProductService priceProductService = null;
        try {
            priceProductService = new PriceProductService();
        } catch (IOException e) {
            System.out.println("Error loading products. Exiting program.");
            return;
        }
        PricingBasketApp app = new PricingBasketApp(priceProductService);
        String input = app.getUserItems();
        if (input.isEmpty()) {
            System.out.println("No items entered. Exiting program.");
            return;
        }
        List<Product> products = app.mapProducts(input);
        System.out.printf("Subtotal: %s%n", new Basket(products).calculateSubtotal());
    }

    private List<Product> mapProducts(String input) {
        return Arrays.stream(input.split("\\s+"))
                .map(item -> {
                    Optional <BigDecimal> price = priceProductService.getPriceProduct(item);
                    if (price.isPresent()) {
                        return new Product(item, price.get());
                    } else {
                        System.out.printf("Unknown item: %s%n", item);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private String getUserItems() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter basket items separated by spaces (e.g., Apples Milk Bread):");
        String input = scanner.nextLine().trim();
        scanner.close();
        return input;
    }
}
