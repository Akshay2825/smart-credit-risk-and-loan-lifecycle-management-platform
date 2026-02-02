package com.crlm.controller;

import com.crlm.dto.LoanApplicationDto;
import com.crlm.service.LoanApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loans")
public class LoanApplicationController{

    private final LoanApplicationService loanService;

    @PostMapping
    public LoanApplicationDto applyLoan(
            @Valid @RequestBody LoanApplicationDto dto) {
        return loanService.applyLoan(dto);
    }

    @GetMapping("/{id}")
    public LoanApplicationDto getById(@PathVariable UUID id) {
        return loanService.getById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<LoanApplicationDto> getByCustomer(
            @PathVariable UUID customerId) {
        return loanService.getByCustomer(customerId);
    }

    @PatchMapping("/{id}/approve")
    public void approve(@PathVariable UUID id) {
        loanService.approveLoan(id);
    }

    @PatchMapping("/{id}/reject")
    public void reject(@PathVariable UUID id) {
        loanService.rejectLoan(id);
    }
}
