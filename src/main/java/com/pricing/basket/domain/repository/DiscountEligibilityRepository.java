package com.pricing.basket.domain.repository;

import com.pricing.basket.domain.model.DiscountEligibility;

import java.io.IOException;
import java.util.List;

public interface DiscountEligibilityRepository {

    List<DiscountEligibility> findAll() throws IOException;
}
