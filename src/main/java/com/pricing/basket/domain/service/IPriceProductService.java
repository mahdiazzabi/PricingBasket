package com.pricing.basket.domain.service;

import com.pricing.basket.domain.model.Product;

import java.util.Optional;

public interface IPriceProductService {
    Product getProduct(String item);
}
