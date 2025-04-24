package com.pricing.basket.domain.model;

import java.math.BigDecimal;

public class DiscountEligibility {

    private String description;
    private BigDecimal discount;
    private ConditionEligibility conditionEligibility;
    private String targetTag;

    public DiscountEligibility() { // For Jackson deserialization
    }

    public DiscountEligibility(String description, BigDecimal discount, ConditionEligibility conditionEligibility, String targetTag) {
        this.description = description;
        this.discount = discount;
        this.conditionEligibility = conditionEligibility;
        this.targetTag = targetTag;
    }

    public static class ConditionEligibility {

        private int minQuantity;
        private String ofTag;
        private String startDate;
        private String endDate;

        public ConditionEligibility() {// For Jackson deserialization
        }

        public ConditionEligibility(String startDate, String endDate, int minQuantity, String ofTag) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.minQuantity = minQuantity;
            this.ofTag = ofTag;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String start_date) {
            this.startDate = start_date;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getMinQuantity() {
            return minQuantity;
        }

        public void setMinQuantity(int minQuantity) {
            this.minQuantity = minQuantity;
        }

        public String getOfTag() {
            return ofTag;
        }

        public void setOfTag(String ofTag) {
            this.ofTag = ofTag;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getTargetTag() {
        return targetTag;
    }

    public void setTargetTag(String targetTag) {
        this.targetTag = targetTag;
    }

    public ConditionEligibility getConditionEligibility() {
        return conditionEligibility;
    }

    public void setConditionEligibility(ConditionEligibility conditionEligibility) {
        this.conditionEligibility = conditionEligibility;
    }
}
