package com.pricing.basket.adapter.service;

import com.pricing.basket.adapter.config.DiscountEligibiliyConfigLoader;
import com.pricing.basket.domain.model.Basket;
import com.pricing.basket.domain.model.DiscountEligibility;
import com.pricing.basket.domain.model.Product;
import com.pricing.basket.domain.service.IBasketService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketService implements IBasketService {

    private List<DiscountEligibility> discountEligibilities;
    private final DiscountEligibiliyConfigLoader loader;

    public BasketService(DiscountEligibiliyConfigLoader discountEligibiliyConfigLoader) {
        this.loader = discountEligibiliyConfigLoader;
    }

    @PostConstruct
    public void init(){
        try {
            // Load discount eligibilities from the configuration file
            this.discountEligibilities = loader.load();
        } catch (IOException e) {
            System.err.println("Failed to load discount eligibilities: " + e.getMessage());
            this.discountEligibilities = List.of();
        }
    }

    @Override
    public Basket applyEligibilityDiscounts(List<Product> products, LocalDate currentDate) {
        Basket basket = new Basket(products);
        basket.setSubtotal(calculateSubtotal(products));
        BigDecimal totalDiscount = BigDecimal.ZERO;
        List<DiscountEligibility> appliedDiscounts = new ArrayList<>();
        for (DiscountEligibility discount : getValidDiscountEligibilities(discountEligibilities, products, currentDate)) {
            BigDecimal discountAmount = products.stream()
                    .filter(p -> p.getTags().contains(discount.getTargetTag()))
                    .map(p -> p.getPrice().multiply(discount.getDiscount()).divide(BigDecimal.valueOf(100)))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                totalDiscount = totalDiscount.add(discountAmount);
                appliedDiscounts.add(discount);
            }
        }
        basket.setTotal(basket.getSubtotal().subtract(totalDiscount).max(BigDecimal.ZERO));
        basket.setDiscounts(appliedDiscounts);
        return basket;
    }

    private List<DiscountEligibility> getValidDiscountEligibilities(List<DiscountEligibility> discountEligibilities, List<Product> products, LocalDate currentDate) {
        return discountEligibilities.stream()
                .filter(discount -> {
                    DiscountEligibility.ConditionEligibility condition = discount.getConditionEligibility();
                    LocalDate startDate = LocalDate.parse(condition.getStartDate());
                    LocalDate endDate = LocalDate.parse(condition.getEndDate());
                    return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
                })
                .filter(discount -> {
                    DiscountEligibility.ConditionEligibility condition = discount.getConditionEligibility();
                    String ofTag = condition.getOfTag();
                    int minQuantity = condition.getMinQuantity();

                    long matchingTagCount = products.stream()
                            .flatMap(product -> product.getTags().stream())
                            .filter(tag -> tag.equals(ofTag))
                            .count();

                    return matchingTagCount >= minQuantity;
                })
                .collect(Collectors.toList());
    }

    private BigDecimal calculateSubtotal(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
