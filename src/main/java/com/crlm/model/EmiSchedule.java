package com.crlm.model;

import com.crlm.enums.EmiStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "emi_schedules",
        indexes = {
                @Index(name = "idx_emi_loan_account", columnList = "loan_account_id"),
                @Index(name = "idx_emi_status", columnList = "status")
        }
)
@Data
public class EmiSchedule extends BaseEntity {

    @Column(name = "loan_account_id", nullable = false, updatable = false)
    private UUID loanAccountId;

    @Column(nullable = false)
    private Integer installmentNumber;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal principalComponent;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal interestComponent;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal emiAmount;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal outstandingBalance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmiStatus status;

    protected EmiSchedule() {}

    private EmiSchedule(
            UUID loanAccountId,
            Integer installmentNumber,
            LocalDate dueDate,
            BigDecimal principalComponent,
            BigDecimal interestComponent,
            BigDecimal emiAmount,
            BigDecimal outstandingBalance
    ) {
        this.loanAccountId = loanAccountId;
        this.installmentNumber = installmentNumber;
        this.dueDate = dueDate;
        this.principalComponent = principalComponent;
        this.interestComponent = interestComponent;
        this.emiAmount = emiAmount;
        this.outstandingBalance = outstandingBalance;
        this.status = EmiStatus.PENDING;
    }

    public static EmiSchedule create(
            UUID loanAccountId,
            Integer installmentNumber,
            LocalDate dueDate,
            BigDecimal principalComponent,
            BigDecimal interestComponent,
            BigDecimal emiAmount,
            BigDecimal outstandingBalance
    ) {
        return new EmiSchedule(
                loanAccountId,
                installmentNumber,
                dueDate,
                principalComponent,
                interestComponent,
                emiAmount,
                outstandingBalance
        );
    }

    public void markPaid() {
        this.status = EmiStatus.PAID;
    }

}

