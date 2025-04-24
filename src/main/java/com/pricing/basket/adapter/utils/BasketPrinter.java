package com.pricing.basket.adapter.utils;
import com.pricing.basket.domain.model.Basket;
import com.pricing.basket.domain.model.DiscountEligibility;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class BasketPrinter {

    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter basket items separated by spaces (e.g., Apples Milk Bread):");
        String input = scanner.nextLine().trim();
        scanner.close();
        return input;
    }

    public static void print(Basket basket) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);

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
        return pennies + "p";
    }

    private static BigDecimal computeDiscountValue(Basket basket, DiscountEligibility discount) {
             return basket.getProducts().stream()
                    .filter(p -> p.getTags().contains(discount.getTargetTag()))
                    .map(p -> p.getPrice().multiply(discount.getDiscount()).divide(BigDecimal.valueOf(100)))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
