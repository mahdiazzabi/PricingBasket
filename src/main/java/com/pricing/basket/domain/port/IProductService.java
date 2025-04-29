package com.pricing.basket.domain.port;

import com.pricing.basket.domain.model.Product;
import org.springframework.stereotype.Service;

@Service
public interface IProductService {
    Product getProduct(String item);
}
