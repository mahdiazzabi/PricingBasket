package com.pricing.basket.adapter.service;

import com.pricing.basket.adapter.config.DiscountEligibiliyConfigLoader;
import com.pricing.basket.domain.model.Basket;
import com.pricing.basket.domain.model.DiscountEligibility;
import com.pricing.basket.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @InjectMocks
    private BasketService basketService;

    @Mock
    private DiscountEligibiliyConfigLoader mockLoader;

    @BeforeEach
    void setUp() throws Exception{
        basketService = new BasketService(mockLoader);
        when(mockLoader.load()).thenReturn(List.of(
                new DiscountEligibility("Discount 10%", BigDecimal.TEN, new DiscountEligibility.ConditionEligibility("2025-01-01", "2025-12-31", 1 , "Tag1"), "Tag1")
        ));
        basketService.init();
    }

    @Test
    void appliesDiscountsCorrectlyWhenEligibleProductsOfOtherTagExist() throws IOException {
        basketService = new BasketService(mockLoader);
        when(mockLoader.load()).thenReturn(List.of(
                new DiscountEligibility("Discount 10%", BigDecimal.TEN, new DiscountEligibility.ConditionEligibility("2025-01-01", "2025-12-31", 2 , "Tag2"), "Tag1")
        ));
        basketService.init();
        List<Product> products = List.of(
                new Product("Product1", new BigDecimal("100"), List.of("Tag1")),
                new Product("Product2", new BigDecimal("50"), List.of("Tag2")),
                new Product("Product2", new BigDecimal("50"), List.of("Tag2"))
        );

        Basket result = basketService.applyEligibilityDiscounts(products, LocalDate.of(2025, 6, 15));

        assertEquals(new BigDecimal("200"), result.getSubtotal());
        assertEquals(new BigDecimal("190"), result.getTotal());
        assertEquals(1, result.getDiscounts().size());
    }

    @Test
    void appliesDiscountsCorrectlyWhenMultipleEligibleDiscountExist() throws IOException {
        basketService = new BasketService(mockLoader);
        when(mockLoader.load()).thenReturn(List.of(
                new DiscountEligibility("Discount 10%", BigDecimal.TEN, new DiscountEligibility.ConditionEligibility("2025-01-01", "2025-12-31", 1 , "Tag1"), "Tag1"),
                new DiscountEligibility("Discount 50%", new BigDecimal(50), new DiscountEligibility.ConditionEligibility("2025-01-01", "2025-12-31", 2 , "Tag2"), "Tag3")
        ));
        basketService.init();
        List<Product> products = List.of(
                new Product("Product1", new BigDecimal("100"), List.of("Tag1")),
                new Product("Product2", new BigDecimal("50"), List.of("Tag2")),
                new Product("Product2", new BigDecimal("50"), List.of("Tag2")),
                new Product("Product3", new BigDecimal("100"), List.of("Tag3"))
        );

        Basket result = basketService.applyEligibilityDiscounts(products, LocalDate.of(2025, 6, 15));

        assertEquals(new BigDecimal("300"), result.getSubtotal());
        assertEquals(new BigDecimal("240"), result.getTotal());
        assertEquals(2, result.getDiscounts().size());
    }

    @Test
    void appliesDiscountsCorrectlyWhenEligibleProductsExist()  {
        List<Product> products = List.of(
                new Product("Product1", new BigDecimal("100"), List.of("Tag1")),
                new Product("Product2", new BigDecimal("50"), List.of("Tag2"))
        );

        Basket result = basketService.applyEligibilityDiscounts(products, LocalDate.of(2025, 6, 15));

        assertEquals(new BigDecimal("150"), result.getSubtotal());
        assertEquals(new BigDecimal("140"), result.getTotal());
        assertEquals(1, result.getDiscounts().size());
    }

    @Test
    void doesNotApplyDiscountsWhenNoEligibleProductsExist() throws Exception {
        List<Product> products = List.of(
                new Product("Product1", new BigDecimal("100"), List.of("Tag2")),
                new Product("Product2", new BigDecimal("50"), List.of("Tag3"))
        );

        Basket result = basketService.applyEligibilityDiscounts(products, LocalDate.of(2025, 6, 15));

        assertEquals(new BigDecimal("150"), result.getSubtotal());
        assertEquals(new BigDecimal("150"), result.getTotal());
        assertEquals(0, result.getDiscounts().size());
    }

    @Test
    void doesNotApplyDiscountsWhenOutsideEligibilityDateRange() {

        List<Product> products = List.of(
                new Product("Product1", new BigDecimal("100"), List.of("Tag1")),
                new Product("Product2", new BigDecimal("50"), List.of("Tag2"))
        );

        Basket result = basketService.applyEligibilityDiscounts(products, LocalDate.of(2023, 6, 15));

        assertEquals(new BigDecimal("150"), result.getSubtotal());
        assertEquals(new BigDecimal("150"), result.getTotal());
        assertEquals(0, result.getDiscounts().size());
    }
}