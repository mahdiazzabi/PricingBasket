package main.java.com.pricing.basket;

import main.java.com.pricing.basket.model.Basket;
import main.java.com.pricing.basket.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PriceBasketApp {
    private static final Map<String, BigDecimal> priceList = Map.of(
            "Soup", new BigDecimal("0.65"),
            "Bread", new BigDecimal("0.80"),
            "Milk", new BigDecimal("1.30"),
            "Apples", new BigDecimal("1.00")
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter basket items separated by spaces (e.g., Apples Milk Bread):");
        String input = scanner.nextLine().trim();
        scanner.close();

        if (input.isEmpty()) {
            System.out.println("No items entered. Exiting program.");
            return;
        }

        List<Product> products = new ArrayList<>();
        for (String item : input.split("\\s+")) {
            BigDecimal price = priceList.get(item);
            if (price != null) {
                products.add(new Product(item, price));
            } else {
                System.out.printf("Unknown item: %s%n", item);
            }
        }

        System.out.printf("Subtotal: %s%n", new Basket(products).calculateSubtotal());
    }
}
