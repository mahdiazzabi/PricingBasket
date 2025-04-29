package com.pricing.basket.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.basket.domain.model.DiscountEligibility;
import com.pricing.basket.infra.port.repository.DiscountEligibilityRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class DiscountEligibilityConfigLoader implements DiscountEligibilityRepository {

    public static String RESOURCES_DISCOUNT_ELIGIBILITY_JSON = "src/main/resources/discount-eligibility.json";

    @Override
    public List<DiscountEligibility> findAll() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DiscountEligibilityConfig config = mapper.readValue(new File(RESOURCES_DISCOUNT_ELIGIBILITY_JSON), DiscountEligibilityConfig.class);
        return config.getDiscountEligibility();
    }

    private static class DiscountEligibilityConfig {
        private List<DiscountEligibility> discountEligibility;

        public List<DiscountEligibility> getDiscountEligibility() {
            return discountEligibility;
        }
    }
}
