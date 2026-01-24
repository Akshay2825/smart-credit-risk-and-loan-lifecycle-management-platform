package com.crlm.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class LoanDecisionApproveRequestDTO {

    private UUID applicationId;
    private BigDecimal interestRate;
    private String remarks;

    public UUID getApplicationId() {
        return applicationId;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public String getRemarks() {
        return remarks;
    }
}
