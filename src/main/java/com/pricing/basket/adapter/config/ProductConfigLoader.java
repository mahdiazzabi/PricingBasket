package com.pricing.basket.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.basket.domain.model.Product;
import com.pricing.basket.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Repository
public class ProductConfigLoader implements ProductRepository {

    public static String RESOURCES_PRODUCTS_JSON = "src/main/resources/products.json";

    @Override
    public List<Product> findAll() throws IOException {
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
