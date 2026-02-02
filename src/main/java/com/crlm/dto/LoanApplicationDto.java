package com.crlm.dto;

import com.crlm.enums.LoanApplicationStatus;
import com.crlm.enums.LoanPurpose;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class LoanApplicationDto {

    // response fields
    private UUID id;
    private Instant createdAt;
    private LoanApplicationStatus status;

    // request fields
    @NotNull
    private UUID customerId;

    @NotNull
    @DecimalMin("1000.00")
    private BigDecimal loanAmount;

    @NotNull
    @Min(1)
    private Integer tenureMonths;

    @NotNull
    private LoanPurpose purpose;

    // getters & setters
}
