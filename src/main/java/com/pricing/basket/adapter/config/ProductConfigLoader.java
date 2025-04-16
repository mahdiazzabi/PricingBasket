package com.pricing.basket.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.basket.domain.model.Product;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Service
public class ProductConfigLoader {

    public static String RESOURCES_PRODUCTS_JSON = "src/main/resources/products.json";

    public List<Product> load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ProductConfig config = mapper.readValue(new File(RESOURCES_PRODUCTS_JSON), ProductConfig.class);
        return config.getProducts();
    }

    private static class ProductConfig {
        private List<Product> products;

        public List<Product> getProducts() {
            return products;
        }
    }
}
