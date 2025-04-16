package com.pricing.basket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.basket.model.Product;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProductConfigLoader {

    public static final String RESOURCES_PRODUCTS_JSON = "src/main/resources/products.json";

    // Charge les produits à partir du fichier JSON
    public static List<Product> load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ProductConfig config = mapper.readValue(new File(RESOURCES_PRODUCTS_JSON), ProductConfig.class);
        return config.getProducts();
    }

    private static class ProductConfig {
        private List<Product> products;

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }
    }
}
