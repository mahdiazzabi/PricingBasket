package com.pricing.basket.adapter.service;

import com.pricing.basket.domain.model.Basket;
import com.pricing.basket.domain.model.DiscountEligibility;
import com.pricing.basket.domain.model.Product;
import com.pricing.basket.domain.service.IBasketService;
import com.pricing.basket.domain.service.IDiscountEvaluatorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketService implements IBasketService {

    private IDiscountEvaluatorService discountEvaluatorService;

    public BasketService(IDiscountEvaluatorService discountEvaluatorService) {
        this.discountEvaluatorService = discountEvaluatorService;
    }

    /**
     * Applies eligibility discounts to a list of products and calculates the total price of the basket.
     *
     * @param products    The list of products in the basket.
     * @param currentDate The current date used to determine discount eligibility.
     * @return A Basket object containing the products, subtotal, total after discounts, and applied discounts.
     */
    @Override
    public Basket applyEligibilityDiscounts(List<Product> products, LocalDate currentDate) {
        Basket basket = new Basket(products);
        basket.setSubtotal(calculateSubtotal(products));

        BigDecimal totalDiscount = BigDecimal.ZERO;
        List<DiscountEligibility> appliedDiscounts = new ArrayList<>();

        // Iterate through valid discount eligibilities
        for (DiscountEligibility discount : discountEvaluatorService.evaluateDiscountEligibility(products, currentDate)) {
            // Calculate the discount amount for products matching the discount's target tag
            BigDecimal discountAmount = products.stream()
                    .filter(p -> p.getTags().contains(discount.getTargetTag()))
                    .map(p -> p.getPrice().multiply(discount.getDiscount()).divide(BigDecimal.valueOf(100)))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // If a discount amount is applicable, add it to the total discount and record the discount
            if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                totalDiscount = totalDiscount.add(discountAmount);
                appliedDiscounts.add(discount);
            }
        }
        // Calculate and set the total price of the basket after applying discounts
        basket.setTotal(basket.getSubtotal().subtract(totalDiscount).max(BigDecimal.ZERO));
        // Set the list of applied discounts in the basket
        basket.setDiscounts(appliedDiscounts);
        return basket;
    }

    private BigDecimal calculateSubtotal(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
