package com.pricing.basket.infra.port;

import com.pricing.basket.domain.model.Basket;

public interface IBasketPrinter {
    String getInput();
    void print(Basket basket);
}
