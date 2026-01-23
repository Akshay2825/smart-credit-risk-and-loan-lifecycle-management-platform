package com.crlm.model;

import com.crlm.enums.Decision;
import com.crlm.enums.LoanStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "loan_accounts",
        indexes = {
                @Index(name = "idx_loan_account_customer", columnList = "customer_id"),
                @Index(name = "idx_loan_account_decision", columnList = "loan_decision_id")
        }
)
public class LoanAccount extends BaseEntity {

    /**
     * Reference to approved LoanDecision.
     * This is PROOF that this loan is valid.
     */
    @Column(name = "loan_decision_id", nullable = false, updatable = false)
    private UUID loanDecisionId;

    /**
     * Customer who owns this loan.
     */
    @Column(name = "customer_id", nullable = false, updatable = false)
    private UUID customerId;

    /**
     * Principal amount approved.
     */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal principalAmount;

    /**
     * Interest rate copied from LoanDecision.
     */
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;

    /**
     * Loan tenure in months.
     */
    @Column(nullable = false)
    private Integer tenureMonths;

    /**
     * Business dates.
     */
    @Column(nullable = false, updatable = false)
    private LocalDate loanStartDate;

    @Column(nullable = false, updatable = false)
    private LocalDate loanEndDate;

    /**
     * Loan lifecycle state.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LoanStatus status;

    protected LoanAccount() {
        // JPA only
    }

    private LoanAccount(
            UUID loanDecisionId,
            UUID customerId,
            BigDecimal principalAmount,
            BigDecimal interestRate,
            Integer tenureMonths,
            LocalDate loanStartDate
    ) {
        this.loanDecisionId = loanDecisionId;
        this.customerId = customerId;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.loanStartDate = loanStartDate;
        this.loanEndDate = loanStartDate.plusMonths(tenureMonths);
        this.status = LoanStatus.ACTIVE;
    }

    /* ---------- Factory ---------- */

    public static LoanAccount createFromApprovedDecision(
            LoanDecision decision,
            UUID customerId,
            BigDecimal principalAmount,
            Integer tenureMonths,
            LocalDate loanStartDate
    ) {
        if (decision.getDecision() != Decision.APPROVED) {
            throw new IllegalStateException(
                    "LoanAccount can only be created from APPROVED LoanDecision"
            );
        }

        return new LoanAccount(
                decision.getApplicationId(),
                customerId,
                principalAmount,
                decision.getInterestRate(),
                tenureMonths,
                loanStartDate
        );
    }

    /* ---------- Getters ---------- */

    public UUID getLoanDecisionId() {
        return loanDecisionId;
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

