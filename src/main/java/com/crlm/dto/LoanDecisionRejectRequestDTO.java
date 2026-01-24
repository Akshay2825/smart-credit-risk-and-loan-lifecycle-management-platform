package com.crlm.dto;

import java.util.UUID;

public class LoanDecisionRejectRequestDTO {

    private UUID applicationId;
    private String remarks;

    public UUID getApplicationId() {
        return applicationId;
    }

    public String getRemarks() {
        return remarks;
    }
}
