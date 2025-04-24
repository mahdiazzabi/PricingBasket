package com.pricing.basket.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.basket.domain.model.DiscountEligibility;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DiscountEligibiliyConfigLoader {

    public static String RESOURCES_DISCOUNT_ELIGIBILITY_JSON = "src/main/resources/discount-eligibility.json";

    public List<DiscountEligibility> load() throws IOException {
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
