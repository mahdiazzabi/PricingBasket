package com.pricing.basket.domain.service;

import java.math.BigDecimal;
import java.util.Optional;

public interface IPriceProductService {
    Optional<BigDecimal> getPriceProduct(String item);
}
