package com.crlm.controller;

import com.crlm.dto.LoanDecisionApproveRequestDTO;
import com.crlm.dto.LoanDecisionRejectRequestDTO;
import com.crlm.dto.LoanDecisionResponseDTO;
import com.crlm.model.LoanDecision;
import com.crlm.service.LoanDecisionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/loan-decisions")
public class LoanDecisionController {

    private final LoanDecisionService loanDecisionService;

    public LoanDecisionController(LoanDecisionService loanDecisionService) {
        this.loanDecisionService = loanDecisionService;
    }

    @PostMapping("/auto-approve")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanDecisionResponseDTO autoApprove(
            @RequestBody LoanDecisionApproveRequestDTO request
    ) {
        LoanDecision decision =
                loanDecisionService.autoApprove(
                        request.getApplicationId(),
                        request.getInterestRate(),
                        request.getRemarks()
                );

        return toResponse(decision);
    }

    @PostMapping("/manual-approve")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanDecisionResponseDTO manualApprove(
            @RequestBody LoanDecisionApproveRequestDTO request
    ) {
        LoanDecision decision =
                loanDecisionService.manualApprove(
                        request.getApplicationId(),
                        request.getInterestRate(),
                        request.getRemarks()
                );

        return toResponse(decision);
    }

    @PostMapping("/reject")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanDecisionResponseDTO reject(
            @RequestBody LoanDecisionRejectRequestDTO request
    ) {
        LoanDecision decision =
                loanDecisionService.reject(
                        request.getApplicationId(),
                        request.getRemarks()
                );

        return toResponse(decision);
    }

    @GetMapping("/{applicationId}")
    public LoanDecisionResponseDTO getDecision(
            @PathVariable UUID applicationId
    ) {
        LoanDecision decision =
                loanDecisionService.getByApplicationId(applicationId);

        return toResponse(decision);
    }

    /* ---------- Mapping ---------- */

    private LoanDecisionResponseDTO toResponse(LoanDecision decision) {
        return new LoanDecisionResponseDTO(
                decision.getApplicationId(),
                decision.getDecision(),
                decision.getInterestRate(),
                decision.getDecidedBy(),
                decision.getRemarks(),
                decision.getDecidedAt()
        );
    }
}
