package com.pricing.basket.adapter.service;

import com.pricing.basket.adapter.config.ProductConfigLoader;
import com.pricing.basket.domain.model.Product;
import com.pricing.basket.domain.service.IPriceProductService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PriceProductService implements IPriceProductService {

    private List<Product> availableProducts;
    private final ProductConfigLoader loader;

    public PriceProductService(ProductConfigLoader productConfigLoader) {
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
    public Optional<BigDecimal> getPriceProduct(String item) {
        Product result = availableProducts.stream()
                .filter(p -> p.getName().equalsIgnoreCase(item))
                .findFirst()
                .orElse(null);
        return Objects.isNull(result) ? Optional.empty() : Optional.ofNullable(result.getPrice());
    }
}
