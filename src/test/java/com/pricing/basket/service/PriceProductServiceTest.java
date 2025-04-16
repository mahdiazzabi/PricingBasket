package com.pricing.basket.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceProductServiceTest {

    @Test
    public void returnsEmptyOptionalForNullProductName() throws IOException {
        PriceProductService service = new PriceProductService();
        Optional<BigDecimal> price = service.getPriceProduct(null);
        assertEquals(Optional.empty(), price);
    }

    @Test
    public void returnsEmptyOptionalForUnknownProduct() throws IOException {
        PriceProductService service = new PriceProductService();
        Optional<BigDecimal> price = service.getPriceProduct("UnknownItem");
        assertEquals(Optional.empty(), price);
    }

    @Test
    public void returnsPriceForKnownProductAsOptional() throws IOException{
        PriceProductService service = new PriceProductService();
        Optional<BigDecimal> price = service.getPriceProduct("Apples");
        assertEquals(Optional.of(new BigDecimal("1.00")), price);
    }

    @Test
    public void handlesEmptyStringAsProductNameWithEmptyOptional() throws IOException {
        PriceProductService service = new PriceProductService();
        Optional<BigDecimal> price = service.getPriceProduct("");
        assertEquals(Optional.empty(), price);
    }

    @Test
    public void handlesCaseSensitivityForProductNamesWithEmptyOptional() throws IOException{
        PriceProductService service = new PriceProductService();
        Optional<BigDecimal> price = service.getPriceProduct("apples");
        assertEquals(Optional.of(new BigDecimal("1.00")), price);
    }
}