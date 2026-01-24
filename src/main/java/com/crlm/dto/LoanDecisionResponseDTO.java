package com.crlm.dto;

import com.crlm.enums.Decision;
import com.crlm.enums.DecisionBy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class LoanDecisionResponseDTO {

    private UUID applicationId;
    private Decision decision;
    private BigDecimal interestRate;
    private DecisionBy decidedBy;
    private String remarks;
    private Instant decidedAt;

    public LoanDecisionResponseDTO(
            UUID applicationId,
            Decision decision,
            BigDecimal interestRate,
            DecisionBy decidedBy,
            String remarks,
            Instant decidedAt
    ) {
        this.applicationId = applicationId;
        this.decision = decision;
        this.interestRate = interestRate;
        this.decidedBy = decidedBy;
        this.remarks = remarks;
        this.decidedAt = decidedAt;
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

    public Instant getDecidedAt() {
        return decidedAt;
    }
}
