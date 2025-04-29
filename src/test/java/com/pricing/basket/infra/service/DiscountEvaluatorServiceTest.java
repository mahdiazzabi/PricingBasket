package com.pricing.basket.infra.service;

import com.pricing.basket.domain.model.DiscountEligibility;
import com.pricing.basket.domain.model.Product;
import com.pricing.basket.domain.service.DiscountEvaluatorService;
import com.pricing.basket.infra.port.repository.DiscountEligibilityRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountEvaluatorServiceTest {

    @InjectMocks
    DiscountEvaluatorService discountEvaluatorService;
    @Mock
    private DiscountEligibilityRepository discountEligibilityRepository;

    @BeforeEach
    void setUp() throws Exception {
        when(discountEligibilityRepository.findAll()).thenReturn(List.of(
                new DiscountEligibility("Apples 10% off", BigDecimal.TEN, new DiscountEligibility.ConditionEligibility("2025-01-01", "2025-12-31", 1 , "Apples"), "Apples")
        ));
        discountEvaluatorService.init();
    }

    @Test
    void evaluateDiscountEligibility_appliesDiscountWithinDateRange() {
        List<Product> products = List.of(new Product("Apple", BigDecimal.ONE, List.of("Apples")));
        LocalDate currentDate = LocalDate.of(2025, 4, 15);

        List<DiscountEligibility> result = discountEvaluatorService.evaluateDiscountEligibility(products, currentDate);

        assertEquals(1, result.size());
        assertEquals("Apples 10% off", result.get(0).getDescription());
    }

    @Test
    void evaluateDiscountEligibility_noDiscountOutsideDateRange() {
        List<Product> products = List.of(new Product("Apple", BigDecimal.ONE, List.of("Apples")));
        LocalDate currentDate = LocalDate.of(3000, 5, 1);

        List<DiscountEligibility> result = discountEvaluatorService.evaluateDiscountEligibility(products, currentDate);

        assertTrue(result.isEmpty());
    }

    @Test
    void evaluateDiscountEligibility_noDiscountIfMinQuantityNotMet() {
        List<Product> products = List.of(new Product("Soup", BigDecimal.ONE, List.of("Soup")));
        LocalDate currentDate = LocalDate.of(2025, 4, 15);

        List<DiscountEligibility> result = discountEvaluatorService.evaluateDiscountEligibility(products, currentDate);

        assertTrue(result.isEmpty());
    }

    @Test
    void evaluateDiscountEligibility_appliesMultipleDiscounts() throws IOException {
        List<Product> products = List.of(
                new Product("Apple", BigDecimal.ONE, List.of("Apples")),
                new Product("Soup", BigDecimal.ONE, List.of("Soup")),
                new Product("Soup", BigDecimal.ONE, List.of("Soup"))
        );
        LocalDate currentDate = LocalDate.of(2025, 4, 15);

        when(discountEligibilityRepository.findAll()).thenReturn(List.of(
                new DiscountEligibility("Apples 10% off", BigDecimal.TEN, new DiscountEligibility.ConditionEligibility("2025-01-01", "2025-12-31", 1 , "Apples"), "Apples") ,
                new DiscountEligibility("Bread 50% off", new BigDecimal(50), new DiscountEligibility.ConditionEligibility("2025-01-01", "2025-12-31", 2 , "Soup"), "Bread")
        ));
        discountEvaluatorService.init();

        List<DiscountEligibility> result = discountEvaluatorService.evaluateDiscountEligibility(products, currentDate);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(d -> d.getDescription().equals("Apples 10% off")));
        assertTrue(result.stream().anyMatch(d -> d.getDescription().equals("Bread 50% off")));
    }

    @Test
    void evaluateDiscountEligibility_noDiscountForEmptyProductList() {
        List<Product> products = List.of();
        LocalDate currentDate = LocalDate.of(2025, 4, 15);

        List<DiscountEligibility> result = discountEvaluatorService.evaluateDiscountEligibility(products, currentDate);

        assertTrue(result.isEmpty());
    }
}
