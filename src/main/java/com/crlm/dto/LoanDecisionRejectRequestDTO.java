package com.crlm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class LoanDecisionRejectRequestDTO {

    @NotNull(message = "Application ID is required")
    private UUID applicationId;

    @NotBlank(message = "Remarks are required for rejection")
    @Size(max = 500)
    private String remarks;
}
