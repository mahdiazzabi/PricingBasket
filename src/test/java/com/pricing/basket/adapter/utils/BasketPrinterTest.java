package com.pricing.basket.adapter.utils;

import com.pricing.basket.adapter.config.AppProperties;
import com.pricing.basket.domain.model.Basket;
import com.pricing.basket.domain.model.DiscountEligibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketPrinterTest {

    private BasketPrinter basketPrinter;
    private AppProperties appProperties;

    @BeforeEach
    void setUp() {
        appProperties = mock(AppProperties.class);
        when(appProperties.getParsedLocale()).thenReturn(Locale.UK);
        basketPrinter = new BasketPrinter(appProperties);
    }

    @Test
    void printsCorrectSubtotalAndTotalToConsole_PoundCurrency() {
        // Rediriger la sortie standard
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        AppProperties appProperties = mock(AppProperties.class);
        when(appProperties.getParsedLocale()).thenReturn(Locale.UK);
        BasketPrinter basketPrinter = new BasketPrinter(appProperties);

        Basket basket = mock(Basket.class);
        when(basket.getSubtotal()).thenReturn(BigDecimal.valueOf(10.50));
        when(basket.getTotal()).thenReturn(BigDecimal.valueOf(9.50));
        when(basket.getDiscounts()).thenReturn(List.of());

        basketPrinter.print(basket);

        // Vérifier la sortie console
        String output = outContent.toString();
        assertTrue(output.contains("Subtotal: £10.50"));
        assertTrue(output.contains("Total: £9.50"));
        assertTrue(output.contains("(No offers available)"));

        // Réinitialiser la sortie standard
        System.setOut(System.out);
    }

    @Test
    void printsCorrectSubtotalAndTotalToConsole_FRCurrency() {
        // Rediriger la sortie standard
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        AppProperties appProperties = mock(AppProperties.class);
        when(appProperties.getParsedLocale()).thenReturn(Locale.FRANCE);
        BasketPrinter basketPrinter = new BasketPrinter(appProperties);

        Basket basket = mock(Basket.class);
        when(basket.getSubtotal()).thenReturn(BigDecimal.valueOf(10.50));
        when(basket.getTotal()).thenReturn(BigDecimal.valueOf(9.50));
        when(basket.getDiscounts()).thenReturn(List.of());

        basketPrinter.print(basket);

        // Vérifier la sortie console
        String output = outContent.toString();
        assertTrue(output.contains("€"));
        assertTrue(output.contains("€"));

        // Réinitialiser la sortie standard
        System.setOut(System.out);
    }

}