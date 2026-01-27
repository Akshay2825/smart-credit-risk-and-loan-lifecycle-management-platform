package com.crlm.controller;

import com.crlm.dto.LoanAccountCreateRequestDTO;
import com.crlm.dto.LoanAccountResponseDTO;
import com.crlm.model.LoanAccount;
import com.crlm.service.LoanAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/loan-accounts")
public class LoanAccountController {

    private final LoanAccountService loanAccountService;

    public LoanAccountController(LoanAccountService loanAccountService) {
        this.loanAccountService = loanAccountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanAccountResponseDTO createLoanAccount(
            @Valid @RequestBody LoanAccountCreateRequestDTO request
    ) {
        LoanAccount account =
                loanAccountService.createLoanAccount(
                        request.getApplicationId(),
                        request.getCustomerId(),
                        request.getPrincipalAmount(),
                        request.getTenureMonths(),
                        request.getLoanStartDate()
                );

        return toResponse(account);
    }

    @GetMapping("/{loanAccountId}")
    public LoanAccountResponseDTO getLoanAccount(
            @PathVariable UUID loanAccountId
    ) {
        LoanAccount account =
                loanAccountService.getById(loanAccountId);

        return toResponse(account);
    }

    @GetMapping("/customer/{customerId}")
    public List<LoanAccountResponseDTO> getLoansForCustomer(
            @PathVariable UUID customerId
    ) {
        return loanAccountService.getByCustomerId(customerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private LoanAccountResponseDTO toResponse(LoanAccount account) {
        return new LoanAccountResponseDTO(
                account.getId(),
                account.getCustomerId(),
                account.getPrincipalAmount(),
                account.getInterestRate(),
                account.getTenureMonths(),
                account.getLoanStartDate(),
                account.getLoanEndDate(),
                account.getStatus()
        );
    }
}

