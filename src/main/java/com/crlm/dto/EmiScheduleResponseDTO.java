package com.crlm.dto;

import com.crlm.enums.EmiStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmiScheduleResponseDTO {

    private Integer installmentNumber;
    private LocalDate dueDate;
    private BigDecimal principalComponent;
    private BigDecimal interestComponent;
    private BigDecimal emiAmount;
    private BigDecimal outstandingBalance;
    private EmiStatus status;

    public EmiScheduleResponseDTO(
            Integer installmentNumber,
            LocalDate dueDate,
            BigDecimal principalComponent,
            BigDecimal interestComponent,
            BigDecimal emiAmount,
            BigDecimal outstandingBalance,
            EmiStatus status
    ) {
        this.installmentNumber = installmentNumber;
        this.dueDate = dueDate;
        this.principalComponent = principalComponent;
        this.interestComponent = interestComponent;
        this.emiAmount = emiAmount;
        this.outstandingBalance = outstandingBalance;
        this.status = status;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BigDecimal getPrincipalComponent() {
        return principalComponent;
    }

    public BigDecimal getInterestComponent() {
        return interestComponent;
    }

    public BigDecimal getEmiAmount() {
        return emiAmount;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public EmiStatus getStatus() {
        return status;
    }
}

