package com.crlm.serviceImpl;

import com.crlm.enums.Decision;
import com.crlm.exception.BusinessRuleViolationException;
import com.crlm.exception.ResourceNotFoundException;
import com.crlm.model.LoanAccount;
import com.crlm.model.LoanDecision;
import com.crlm.repository.LoanAccountRepository;
import com.crlm.repository.LoanDecisionRepository;
import com.crlm.service.LoanAccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class LoanAccountServiceImpl implements LoanAccountService {

    private final LoanDecisionRepository loanDecisionRepository;
    private final LoanAccountRepository loanAccountRepository;

    public LoanAccountServiceImpl(
            LoanDecisionRepository loanDecisionRepository,
            LoanAccountRepository loanAccountRepository
    ) {
        this.loanDecisionRepository = loanDecisionRepository;
        this.loanAccountRepository = loanAccountRepository;
    }

    /**
     * Creates LoanAccount ONLY for an approved LoanDecision.
     */
    @Override
    @Transactional
    public LoanAccount createLoanAccount(
            UUID applicationId,
            UUID customerId,
            BigDecimal principalAmount,
            Integer tenureMonths,
            LocalDate loanStartDate
    ) {

        // 1️⃣ Fetch LoanDecision (must exist)
        LoanDecision decision =
                loanDecisionRepository.findByApplicationId(applicationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "LoanDecision not found for applicationId: " + applicationId
                                )
                        );

        // 2️⃣ Decision must be APPROVED
        if (decision.getDecision() != Decision.APPROVED) {
            throw new BusinessRuleViolationException(
                    "LoanAccount can only be created for APPROVED LoanDecision"
            );
        }

        // 3️⃣ Prevent duplicate LoanAccount creation
        boolean accountExists =
                loanAccountRepository.existsByLoanDecisionId(decision.getId());

        if (accountExists) {
            throw new BusinessRuleViolationException(
                    "LoanAccount already exists for this LoanDecision"
            );
        }

        // 4️⃣ Create LoanAccount using factory method
        LoanAccount account =
                LoanAccount.createFromApprovedDecision(
                        decision,
                        customerId,
                        principalAmount,
                        tenureMonths,
                        loanStartDate
                );

        // 5️⃣ Persist and return
        return loanAccountRepository.save(account);
    }

    /**
     * Fetch LoanAccount by ID.
     */
    @Override
    public LoanAccount getById(UUID loanAccountId) {
        return loanAccountRepository.findById(loanAccountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "LoanAccount not found with id: " + loanAccountId
                        )
                );
    }

    /**
     * Fetch all LoanAccounts for a customer.
     */
    @Override
    public List<LoanAccount> getByCustomerId(UUID customerId) {
        return loanAccountRepository.findAllByCustomerId(customerId);
    }
}
