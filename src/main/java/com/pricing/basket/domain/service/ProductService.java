package com.pricing.basket.domain.service;

import com.pricing.basket.domain.model.Product;
import com.pricing.basket.infra.port.repository.ProductRepository;
import com.pricing.basket.domain.port.IProductService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService implements IProductService {

    private List<Product> availableProducts;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void init(){
        try {
            // Load products from the configuration file
            this.availableProducts = productRepository.findAll();
        } catch (IOException e) {
            System.err.println("Failed to load products: " + e.getMessage());
            this.availableProducts = List.of();
        }
    }

    /**
     * Retrieves a product by its name from the list of available products.
     *
     * @param item The name of the product to retrieve.
     * @return The Product object if found, or null if no product matches the given name.
     */
    @Override
    public Product getProduct(String item) {
        return availableProducts.stream()
                .filter(p -> p.getName().equalsIgnoreCase(item))
                .findFirst()
                .orElse(null);
    }
}
