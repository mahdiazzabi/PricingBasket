package com.pricing.basket.domain.service;

import com.pricing.basket.domain.model.Basket;
import com.pricing.basket.domain.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IBasketService {
    Basket applyEligibilityDiscounts(List<Product> products, LocalDate currentDate);
}
