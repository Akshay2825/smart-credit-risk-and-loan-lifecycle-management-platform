package com.crlm.serviceImpl;

import com.crlm.dto.LoanApplicationDto;
import com.crlm.enums.LoanApplicationStatus;
import com.crlm.exception.BusinessRuleException;
import com.crlm.exception.ResourceNotFoundException;
import com.crlm.model.Customer;
import com.crlm.model.LoanApplication;
import com.crlm.repository.CustomerRepository;
import com.crlm.repository.LoanApplicationRepository;
import com.crlm.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanRepo;
    private final CustomerRepository customerRepo;

    @Override
    public LoanApplicationDto applyLoan(LoanApplicationDto dto) {

        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        LoanApplication loan = new LoanApplication();
        loan.setCustomer(customer);
        loan.setLoanAmount(dto.getLoanAmount());
        loan.setTenureMonths(dto.getTenureMonths());
        loan.setPurpose(dto.getPurpose());
        loan.setStatus(LoanApplicationStatus.SUBMITTED);

        return map(loanRepo.save(loan));
    }

    @Override
    public LoanApplicationDto getById(UUID id) {
        return map(loanRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found")));
    }

    @Override
    public List<LoanApplicationDto> getByCustomer(UUID customerId) {
        return loanRepo.findByCustomerId(customerId)
                .stream().map(this::map).toList();
    }

    @Override
    public void approveLoan(UUID loanId) {
        LoanApplication loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        if (loan.getStatus() != LoanApplicationStatus.SUBMITTED)
            throw new BusinessRuleException("Loan already processed");

        loan.setStatus(LoanApplicationStatus.APPROVED);
    }

    @Override
    public void rejectLoan(UUID loanId) {
        LoanApplication loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        if (loan.getStatus() != LoanApplicationStatus.SUBMITTED)
            throw new BusinessRuleException("Loan already processed");

        loan.setStatus(LoanApplicationStatus.REJECTED);
    }

    private LoanApplicationDto map(LoanApplication l) {
        LoanApplicationDto d = new LoanApplicationDto();
        d.setId(l.getId());
        d.setCustomerId(l.getCustomer().getId());
        d.setLoanAmount(l.getLoanAmount());
        d.setTenureMonths(l.getTenureMonths());
        d.setPurpose(l.getPurpose());
        d.setStatus(l.getStatus());
        d.setCreatedAt(l.getCreatedAt());
        return d;
    }
}
