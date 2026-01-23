package com.crlm.model;

import com.crlm.enums.Decision;
import com.crlm.enums.DecisionBy;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "loan_decisions",
        indexes = {
                @Index(name = "idx_loan_decision_application_id", columnList = "application_id"),
                @Index(name = "idx_loan_decision_decision", columnList = "decision")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_loan_decision_application",
                        columnNames = "application_id"
                )
        }
)
public class LoanDecision extends BaseEntity {

    /**
     * Reference to LoanApplication (NO JPA relationship by design).
     */
    @Column(name = "application_id", nullable = false, updatable = false)
    private UUID applicationId;

    /**
     * APPROVED or REJECTED
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Decision decision;

    /**
     * Interest rate applicable ONLY when decision is APPROVED.
     */
    @Column(precision = 5, scale = 2)
    private BigDecimal interestRate;

    /**
     * SYSTEM (auto) or LOAN_OFFICER (manual)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DecisionBy decidedBy;

    /**
     * Reason for approval / rejection.
     */
    @Column(length = 500)
    private String remarks;

    /**
     * BUSINESS timestamp – when the decision was actually taken.
     * This is NOT auditing.
     */
    @Column(nullable = false, updatable = false)
    private Instant decidedAt;

    protected LoanDecision() {
        // JPA only
    }

    private LoanDecision(
            UUID applicationId,
            Decision decision,
            BigDecimal interestRate,
            DecisionBy decidedBy,
            String remarks
    ) {
        this.applicationId = applicationId;
        this.decision = decision;
        this.interestRate = interestRate;
        this.decidedBy = decidedBy;
        this.remarks = remarks;
        this.decidedAt = Instant.now(); // explicit, business-owned
    }

    /* ---------- Factory methods ---------- */

    public static LoanDecision autoApproved(
            UUID applicationId,
            BigDecimal interestRate,
            String remarks
    ) {
        return new LoanDecision(
                applicationId,
                Decision.APPROVED,
                interestRate,
                DecisionBy.SYSTEM,
                remarks
        );
    }

    public static LoanDecision manuallyApproved(
            UUID applicationId,
            BigDecimal interestRate,
            String remarks
    ) {
        return new LoanDecision(
                applicationId,
                Decision.APPROVED,
                interestRate,
                DecisionBy.LOAN_OFFICER,
                remarks
        );
    }

    public static LoanDecision rejected(
            UUID applicationId,
            DecisionBy decidedBy,
            String remarks
    ) {
        return new LoanDecision(
                applicationId,
                Decision.REJECTED,
                null,
                decidedBy,
                remarks
        );
    }

    /* ---------- Business invariants ---------- */

    @PrePersist
    private void validate() {
        if (decision == Decision.APPROVED && interestRate == null) {
            throw new IllegalStateException(
                    "Approved loan decision must have an interest rate"
            );
        }

        if (decision == Decision.REJECTED && interestRate != null) {
            throw new IllegalStateException(
                    "Rejected loan decision must not have an interest rate"
            );
        }
    }

    /* ---------- Getters ---------- */

    public UUID getApplicationId() {
        return applicationId;
    }

    public Decision getDecision() {
        return decision;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public DecisionBy getDecidedBy() {
        return decidedBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public Instant getDecidedAt() {
        return decidedAt;
    }
}
