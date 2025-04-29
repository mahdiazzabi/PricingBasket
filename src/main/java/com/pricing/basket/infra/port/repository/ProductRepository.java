package com.pricing.basket.infra.port.repository;

import com.pricing.basket.domain.model.Product;

import java.io.IOException;
import java.util.List;

public interface ProductRepository {

    List<Product> findAll() throws IOException;
}
