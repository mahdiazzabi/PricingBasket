package com.pricing.basket.app;

import com.pricing.basket.domain.model.Basket;
import com.pricing.basket.domain.model.Product;
import com.pricing.basket.domain.port.IBasketService;
import com.pricing.basket.domain.port.IProductService;
import com.pricing.basket.infra.port.IBasketPrinter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication(scanBasePackages = {
        "com.pricing.basket.domain," +
                "com.pricing.basket.infra"
})
public class PricingBasketApp implements ApplicationRunner {

    private final IProductService productService;
    private final IBasketService basketService;
    private final IBasketPrinter basketPrinter;

    public PricingBasketApp(IProductService productService, IBasketService basketService, IBasketPrinter basketPrinter) {
        this.productService = productService;
        this.basketService = basketService;
        this.basketPrinter = basketPrinter;
    }

    public static void main(String[] args){
        SpringApplication.run(PricingBasketApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args){
        String input = basketPrinter.getInput();
        if (input.isEmpty()) {
            System.out.println("No items entered. Exiting program.");
            return;
        }

        List<Product> products = this.mapProducts(input);
        Basket basket = basketService.applyEligibilityDiscounts(products, LocalDate.now());

        basketPrinter.print(basket);
    }

    private List<Product> mapProducts(String input) {
        return Arrays.stream(input.split("\\s+"))
                .map(item -> {
                    Product product = productService.getProduct(item);
                    if (!Objects.isNull(product)) {
                        return product;
                    } else {
                        System.out.printf("Unknown item: %s%n", item);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
