package com.pricing.basket.domain.service;

import com.pricing.basket.domain.model.Basket;

public interface IBasketPrinter {
    String getInput();
    void print(Basket basket);
}
