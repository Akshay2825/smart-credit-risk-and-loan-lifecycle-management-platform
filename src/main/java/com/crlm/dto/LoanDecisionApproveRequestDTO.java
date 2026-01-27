package com.crlm.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class LoanDecisionApproveRequestDTO {

    @NotNull(message = "Application ID is required")
    private UUID applicationId;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.01", message = "Interest rate must be positive")
    @DecimalMax(value = "50.00", message = "Interest rate too high")
    private BigDecimal interestRate;

    @Size(max = 500, message = "Remarks too long")
    private String remarks;
}

