package com.pricing.basket;

import com.pricing.basket.domain.model.Basket;
import com.pricing.basket.domain.model.Product;
import com.pricing.basket.adapter.service.PriceProductService;
import com.pricing.basket.domain.service.IPriceProductService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.*;

@SpringBootApplication
public class PricingBasketApp implements ApplicationRunner {

    private final IPriceProductService priceProductService;

    public PricingBasketApp(IPriceProductService priceProductService) {
        this.priceProductService = priceProductService;
    }

    public static void main(String[] args){
        SpringApplication.run(PricingBasketApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String input = this.getUserItems();
        if (input.isEmpty()) {
            System.out.println("No items entered. Exiting program.");
            return;
        }
        List<Product> products = this.mapProducts(input);
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
