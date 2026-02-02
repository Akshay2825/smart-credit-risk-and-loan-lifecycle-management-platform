package com.crlm.service;

import com.crlm.dto.LoanApplicationDto;

import java.util.List;
import java.util.UUID;

public interface LoanApplicationService {

    LoanApplicationDto applyLoan(LoanApplicationDto dto);

    LoanApplicationDto getById(UUID id);

    List<LoanApplicationDto> getByCustomer(UUID customerId);

    void approveLoan(UUID loanId);

    void rejectLoan(UUID loanId);
}
