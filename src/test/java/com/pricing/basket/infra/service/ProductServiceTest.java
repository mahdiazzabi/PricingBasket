package com.pricing.basket.infra.service;

import com.pricing.basket.domain.model.Product;
import com.pricing.basket.domain.service.ProductService;
import com.pricing.basket.infra.port.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    public void setUp() throws IOException {
        when(productRepository.findAll()).thenReturn(List.of(new Product("Apples", BigDecimal.TEN, List.of("Fruits" , "bag", "Apples"))));
        service.init();
    }

    @Test
    public void returnsNullForNullProductName() {
        Product product = service.getProduct(null);
        assertNull(product);
    }

    @Test
    public void handlesEmptyStringAsProductNameWithEmpty() {
        Product product = service.getProduct("");
        assertNull(product);
    }


    @Test
    public void returnsNullForUnknownProduct() {
        Product product= service.getProduct("UnknownItem");
        assertNull(product);
    }

    @Test
    public void returnsProductForKnownProductAsOptional() {
        Product product = service.getProduct("Apples");
        assertEquals("Apples", product.getName());
    }

    @Test
    public void handlesCaseSensitivityForProductNames() {
        Product product = service.getProduct("apples");
        assertEquals("Apples", product.getName());
    }
}