package com.pricing.basket.domain.port;

import com.pricing.basket.domain.model.DiscountEligibility;
import com.pricing.basket.domain.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface IDiscountEvaluatorService {

    List<DiscountEligibility> evaluateDiscountEligibility(List<Product> products, LocalDate currentDate);

}
