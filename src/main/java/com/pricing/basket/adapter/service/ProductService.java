package com.pricing.basket.adapter.service;

import com.pricing.basket.adapter.config.ProductConfigLoader;
import com.pricing.basket.domain.model.Product;
import com.pricing.basket.domain.service.IPriceProductService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService implements IPriceProductService {

    private List<Product> availableProducts;
    private final ProductConfigLoader loader;

    public ProductService(ProductConfigLoader productConfigLoader) {
        this.loader = productConfigLoader;
    }

    @PostConstruct
    public void init(){
        try {
            // Load products from the configuration file
            this.availableProducts = loader.load();
        } catch (IOException e) {
            System.err.println("Failed to load products: " + e.getMessage());
            this.availableProducts = List.of();
        }
    }

    @Override
    public Product getProduct(String item) {
        return availableProducts.stream()
                .filter(p -> p.getName().equalsIgnoreCase(item))
                .findFirst()
                .orElse(null);
    }
}
