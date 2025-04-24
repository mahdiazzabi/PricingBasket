package com.pricing.basket;

import com.pricing.basket.adapter.utils.BasketPrinter;
import com.pricing.basket.domain.model.Basket;
import com.pricing.basket.domain.model.Product;
import com.pricing.basket.domain.service.IBasketService;
import com.pricing.basket.domain.service.IPriceProductService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class PricingBasketApp implements ApplicationRunner {

    private final IPriceProductService priceProductService;
    private final IBasketService basketService;

    public PricingBasketApp(IPriceProductService priceProductService, IBasketService basketService) {
        this.priceProductService = priceProductService;
        this.basketService = basketService;
    }

    public static void main(String[] args){
        SpringApplication.run(PricingBasketApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args){
        String input = BasketPrinter.getInput();
        if (input.isEmpty()) {
            System.out.println("No items entered. Exiting program.");
            return;
        }

        List<Product> products = this.mapProducts(input);
        Basket basket = basketService.applyEligibilityDiscounts(products, LocalDate.now());

        BasketPrinter.print(basket);
    }

    private List<Product> mapProducts(String input) {
        return Arrays.stream(input.split("\\s+"))
                .map(item -> {
                    Product product = priceProductService.getProduct(item);
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
