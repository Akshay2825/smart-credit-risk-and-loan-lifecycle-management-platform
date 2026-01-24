package com.crlm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class LoanAccountCreateRequestDTO {

    private UUID applicationId;
    private UUID customerId;
    private BigDecimal principalAmount;
    private Integer tenureMonths;
    private LocalDate loanStartDate;

    public UUID getApplicationId() {
        return applicationId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public Integer getTenureMonths() {
        return tenureMonths;
    }

    public LocalDate getLoanStartDate() {
        return loanStartDate;
    }
}

