package com.pricing.basket.service;

import com.pricing.basket.config.ProductConfigLoader;
import com.pricing.basket.model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PriceProductService {

    private final List<Product> availableProducts;

    public PriceProductService() throws IOException {
        // Charger les produits depuis le fichier de configuration
        ProductConfigLoader loader = new ProductConfigLoader();
        this.availableProducts = loader.load();
    }

    public Optional<BigDecimal> getPriceProduct(String item) {
        Product result = availableProducts.stream()
                .filter(p -> p.getName().equalsIgnoreCase(item))
                .findFirst()
                .orElse(null);
        return Objects.isNull(result) ? Optional.empty() : Optional.ofNullable(result.getPrice());
    }
}
