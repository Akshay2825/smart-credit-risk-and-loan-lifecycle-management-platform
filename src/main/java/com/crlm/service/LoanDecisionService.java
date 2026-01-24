package com.crlm.service;

import com.crlm.model.LoanDecision;

import java.math.BigDecimal;
import java.util.UUID;

public interface LoanDecisionService {

    LoanDecision autoApprove(
            UUID applicationId,
            BigDecimal interestRate,
            String remarks
    );

    LoanDecision manualApprove(
            UUID applicationId,
            BigDecimal interestRate,
            String remarks
    );

    LoanDecision reject(
            UUID applicationId,
            String remarks
    );

    LoanDecision getByApplicationId(UUID applicationId);
}

