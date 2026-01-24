package com.crlm.controller;

import com.crlm.dto.LoanAccountCreateRequestDTO;
import com.crlm.dto.LoanAccountResponseDTO;
import com.crlm.model.LoanAccount;
import com.crlm.service.LoanAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loan-accounts")
public class LoanAccountController {

    private final LoanAccountService loanAccountService;

    public LoanAccountController(LoanAccountService loanAccountService) {
        this.loanAccountService = loanAccountService;
    }

    /**
     * Create LoanAccount from approved LoanDecision.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanAccountResponseDTO createLoanAccount(
            @RequestBody LoanAccountCreateRequestDTO request
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

    /**
     * Fetch single LoanAccount.
     */
    @GetMapping("/{loanAccountId}")
    public LoanAccountResponseDTO getLoanAccount(
            @PathVariable UUID loanAccountId
    ) {
        LoanAccount account =
                loanAccountService.getById(loanAccountId);

        return toResponse(account);
    }

    /**
     * Fetch all loans for a customer.
     */
    @GetMapping("/customer/{customerId}")
    public List<LoanAccountResponseDTO> getLoansForCustomer(
            @PathVariable UUID customerId
    ) {
        return loanAccountService.getByCustomerId(customerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /* ---------- Mapping ---------- */

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
