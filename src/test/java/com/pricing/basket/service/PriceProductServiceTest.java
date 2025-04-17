package com.pricing.basket.service;

import com.pricing.basket.adapter.service.PriceProductService;
import com.pricing.basket.adapter.config.ProductConfigLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceProductServiceTest {
    private PriceProductService service;

    @BeforeEach
    public void setUp() {
        ProductConfigLoader productConfigLoader = new ProductConfigLoader();
        service = new PriceProductService(productConfigLoader);
        service.init();
    }

    @Test
    public void returnsEmptyOptionalForNullProductName() {
        Optional<BigDecimal> price = service.getPriceProduct(null);
        assertEquals(Optional.empty(), price);
    }

    @Test
    public void returnsEmptyOptionalForUnknownProduct() {
        Optional<BigDecimal> price = service.getPriceProduct("UnknownItem");
        assertEquals(Optional.empty(), price);
    }

    @Test
    public void returnsPriceForKnownProductAsOptional() {
        Optional<BigDecimal> price = service.getPriceProduct("Apples");
        assertEquals(Optional.of(new BigDecimal("1.00")), price);
    }

    @Test
    public void handlesEmptyStringAsProductNameWithEmptyOptional() {
        Optional<BigDecimal> price = service.getPriceProduct("");
        assertEquals(Optional.empty(), price);
    }

    @Test
    public void handlesCaseSensitivityForProductNamesWithEmptyOptional() {
        Optional<BigDecimal> price = service.getPriceProduct("apples");
        assertEquals(Optional.of(new BigDecimal("1.00")), price);
    }
}