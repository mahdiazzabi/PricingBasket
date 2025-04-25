package com.pricing.basket.adapter.utils;
import com.pricing.basket.adapter.config.AppProperties;
import com.pricing.basket.domain.model.Basket;
import com.pricing.basket.domain.model.DiscountEligibility;
import com.pricing.basket.domain.service.IBasketPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

@Service
public class BasketPrinter implements IBasketPrinter {

    private final AppProperties appProperties;
    private static NumberFormat currencyFormat;
    static {
        Locale locale = Locale.getDefault();
        currencyFormat = NumberFormat.getCurrencyInstance(locale);
    }

    public BasketPrinter(AppProperties appProperties) {
        this.appProperties = appProperties;
        currencyFormat = NumberFormat.getCurrencyInstance(appProperties.getParsedLocale());
    }

    @Override
    public String getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter basket items separated by spaces (e.g., Apples Milk Bread):");
        String input = scanner.nextLine().trim();
        scanner.close();
        return input;
    }

    @Override
    public void print(Basket basket) {

        System.out.println("Subtotal: " + currencyFormat.format(basket.getSubtotal()));

        if (basket.getDiscounts() == null || basket.getDiscounts().isEmpty()) {
            System.out.println("(No offers available)");
        } else {
            for (DiscountEligibility discount : basket.getDiscounts()) {
                String desc = discount.getDescription();
                BigDecimal discountAmount = computeDiscountValue(basket, discount);
                System.out.println(desc + ": -" + formatPence(discountAmount));
            }
        }

        System.out.println("Total: " + currencyFormat.format(basket.getTotal()));
    }

    private static String formatPence(BigDecimal amount) {
        int pennies = amount.multiply(BigDecimal.valueOf(100)).intValue();
        if (pennies >= 100) {
            BigDecimal majorUnit = amount.setScale(2, BigDecimal.ROUND_DOWN);
            return majorUnit + " " + currencyFormat.getCurrency().getSymbol();
        }
        return pennies + getSubunitSymbol();
    }

    private static BigDecimal computeDiscountValue(Basket basket, DiscountEligibility discount) {
             return basket.getProducts().stream()
                    .filter(p -> p.getTags().contains(discount.getTargetTag()))
                    .map(p -> p.getPrice().multiply(discount.getDiscount()).divide(BigDecimal.valueOf(100)))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static String getSubunitSymbol() {
        return switch (currencyFormat.getCurrency().getCurrencyCode()) {
            case "GBP" -> "p";
            case "USD", "EUR", "CAD", "AUD" -> "c";
            default -> "";
        };
    }
}
