package com.crlm.dto;

import com.crlm.enums.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class LoanAccountResponseDTO {

    private UUID loanAccountId;
    private UUID customerId;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private Integer tenureMonths;
    private LocalDate loanStartDate;
    private LocalDate loanEndDate;
    private LoanStatus status;

    public LoanAccountResponseDTO(
            UUID loanAccountId,
            UUID customerId,
            BigDecimal principalAmount,
            BigDecimal interestRate,
            Integer tenureMonths,
            LocalDate loanStartDate,
            LocalDate loanEndDate,
            LoanStatus status
    ) {
        this.loanAccountId = loanAccountId;
        this.customerId = customerId;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.loanStartDate = loanStartDate;
        this.loanEndDate = loanEndDate;
        this.status = status;
    }

    public UUID getLoanAccountId() {
        return loanAccountId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public Integer getTenureMonths() {
        return tenureMonths;
    }

    public LocalDate getLoanStartDate() {
        return loanStartDate;
    }

    public LocalDate getLoanEndDate() {
        return loanEndDate;
    }

    public LoanStatus getStatus() {
        return status;
    }
}
