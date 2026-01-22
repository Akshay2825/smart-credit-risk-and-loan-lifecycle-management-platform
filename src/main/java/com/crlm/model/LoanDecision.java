package com.crlm.model;

import com.crlm.enums.Decision;
import com.crlm.enums.DecisionBy;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
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
public class LoanDecision {

    @Id
    @UuidGenerator
    private UUID id;

    /**
     * Reference to LoanApplication (NO JPA relationship by design).
     * Keeps the model microservice-safe and avoids ORM coupling.
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
     * Null for rejected decisions.
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
     * Mandatory for manual decisions.
     */
    @Column(length = 500)
    private String remarks;

    /**
     * Decision timestamp.
     */
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant decidedAt;

    protected LoanDecision() {
        // for JPA
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
    }

    /* ---------- Factory methods (IMPORTANT) ---------- */

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

    /* ---------- Getters only (immutability) ---------- */

    public UUID getId() {
        return id;
    }

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
}
