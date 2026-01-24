package com.crlm.service;

import com.crlm.model.LoanAccount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LoanAccountService {

    LoanAccount createLoanAccount(
            UUID applicationId,
            UUID customerId,
            BigDecimal principalAmount,
            Integer tenureMonths,
            LocalDate loanStartDate
    );

    LoanAccount getById(UUID loanAccountId);

    List<LoanAccount> getByCustomerId(UUID customerId);
}
