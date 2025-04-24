package com.pricing.basket.adapter.service;

import com.pricing.basket.adapter.config.DiscountEligibilityConfigLoader;
import com.pricing.basket.domain.model.DiscountEligibility;
import com.pricing.basket.domain.model.Product;
import com.pricing.basket.domain.repository.DiscountEligibilityRepository;
import com.pricing.basket.domain.service.IDiscountEvaluatorService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountEvaluatorService implements IDiscountEvaluatorService {

    private List<DiscountEligibility> discountEligibilities;
    private final DiscountEligibilityRepository discountEligibilityRepository;

    public DiscountEvaluatorService(DiscountEligibilityRepository discountEligibilityRepository) {
        this.discountEligibilityRepository = discountEligibilityRepository;
    }

    @PostConstruct
    public void init(){
        try {
            // Load discount eligibilities from the configuration file
            this.discountEligibilities = discountEligibilityRepository.findAll();
        } catch (IOException e) {
            System.err.println("Failed to load discount eligibilities: " + e.getMessage());
            this.discountEligibilities = List.of();
        }
    }

    @Override
    public List<DiscountEligibility> evaluateDiscountEligibility(List<Product> products, LocalDate currentDate) {
        // logique extraite de getValidDiscountEligibilities(...)
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
}
