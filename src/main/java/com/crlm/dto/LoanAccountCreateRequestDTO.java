package com.crlm.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class LoanAccountCreateRequestDTO {

    @NotNull(message = "Application ID is required")
    private UUID applicationId;

    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    @NotNull(message = "Principal amount is required")
    @DecimalMin(value = "1000.00", message = "Principal amount must be at least 1000")
    private BigDecimal principalAmount;

    @NotNull(message = "Tenure is required")
    @Min(value = 1, message = "Tenure must be at least 1 month")
    @Max(value = 360, message = "Tenure cannot exceed 360 months")
    private Integer tenureMonths;

    @NotNull(message = "Loan start date is required")
    @FutureOrPresent(message = "Loan start date cannot be in the past")
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
