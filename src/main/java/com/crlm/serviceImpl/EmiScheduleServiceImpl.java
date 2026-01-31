package com.crlm.serviceImpl;

import com.crlm.exception.ResourceNotFoundException;
import com.crlm.model.EmiSchedule;
import com.crlm.repository.EmiScheduleRepository;
import com.crlm.repository.LoanAccountRepository;
import com.crlm.service.EmiScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmiScheduleServiceImpl implements EmiScheduleService {

    private final EmiScheduleRepository emiScheduleRepository;
    private final LoanAccountRepository loanAccountRepository;

    public EmiScheduleServiceImpl(
            EmiScheduleRepository emiScheduleRepository,
            LoanAccountRepository loanAccountRepository
    ) {
        this.emiScheduleRepository = emiScheduleRepository;
        this.loanAccountRepository = loanAccountRepository;
    }

    @Override
    public List<EmiSchedule> getScheduleForLoanAccount(UUID loanAccountId) {

        if (!loanAccountRepository.existsById(loanAccountId)) {
            throw new ResourceNotFoundException(
                    "LoanAccount not found with id: " + loanAccountId
            );
        }

        return emiScheduleRepository
                .findByLoanAccountIdOrderByInstallmentNumber(loanAccountId);
    }
}
