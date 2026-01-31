package com.crlm.serviceImpl;

import com.crlm.enums.Decision;
import com.crlm.exception.BusinessRuleViolationException;
import com.crlm.exception.ResourceNotFoundException;
import com.crlm.model.EmiSchedule;
import com.crlm.model.LoanAccount;
import com.crlm.model.LoanDecision;
import com.crlm.repository.EmiScheduleRepository;
import com.crlm.repository.LoanAccountRepository;
import com.crlm.repository.LoanDecisionRepository;
import com.crlm.service.LoanAccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LoanAccountServiceImpl implements LoanAccountService {

    private final LoanDecisionRepository loanDecisionRepository;
    private final LoanAccountRepository loanAccountRepository;
    private final EmiScheduleRepository emiScheduleRepository;

    public LoanAccountServiceImpl(
            LoanDecisionRepository loanDecisionRepository,
            LoanAccountRepository loanAccountRepository,
            EmiScheduleRepository emiScheduleRepository
    ) {
        this.loanDecisionRepository = loanDecisionRepository;
        this.loanAccountRepository = loanAccountRepository;
        this.emiScheduleRepository = emiScheduleRepository;
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

        // 1️⃣ Fetch LoanDecision
        LoanDecision decision =
                loanDecisionRepository.findByApplicationId(applicationId)
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "LoanDecision not found for applicationId: " + applicationId
                                )
                        );

        // 2️⃣ Must be approved
        if (decision.getDecision() != Decision.APPROVED) {
            throw new IllegalStateException(
                    "LoanAccount can only be created for APPROVED LoanDecision"
            );
        }

        // 3️⃣ Prevent duplicate LoanAccount
        if (loanAccountRepository.existsByLoanDecisionId(decision.getId())) {
            throw new IllegalStateException(
                    "LoanAccount already exists for this LoanDecision"
            );
        }

        // 4️⃣ Create LoanAccount
        LoanAccount account =
                LoanAccount.createFromApprovedDecision(
                        decision,
                        customerId,
                        principalAmount,
                        tenureMonths,
                        loanStartDate
                );

        LoanAccount savedAccount = loanAccountRepository.save(account);

        // 5️⃣ Generate EMI Schedule
        List<EmiSchedule> emis = generateEmiSchedule(savedAccount);

        // 6️⃣ Persist EMI Schedule
        emiScheduleRepository.saveAll(emis);

        return savedAccount;
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

    private List<EmiSchedule> generateEmiSchedule(LoanAccount account) {

        BigDecimal principal = account.getPrincipalAmount();

        BigDecimal monthlyRate =
                account.getInterestRate()
                        .divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);

        int tenureMonths = account.getTenureMonths();

        BigDecimal emi =
                principal.multiply(monthlyRate)
                        .multiply(BigDecimal.ONE.add(monthlyRate).pow(tenureMonths))
                        .divide(
                                BigDecimal.ONE.add(monthlyRate).pow(tenureMonths)
                                        .subtract(BigDecimal.ONE),
                                2,
                                RoundingMode.HALF_UP
                        );

        List<EmiSchedule> schedules = new ArrayList<>();
        BigDecimal outstanding = principal;

        for (int i = 1; i <= tenureMonths; i++) {

            BigDecimal interestComponent =
                    outstanding.multiply(monthlyRate)
                            .setScale(2, RoundingMode.HALF_UP);

            BigDecimal principalComponent =
                    emi.subtract(interestComponent)
                            .setScale(2, RoundingMode.HALF_UP);

            outstanding =
                    outstanding.subtract(principalComponent)
                            .max(BigDecimal.ZERO);

            schedules.add(
                    EmiSchedule.create(
                            account.getId(),
                            i,
                            account.getLoanStartDate().plusMonths(i),
                            principalComponent,
                            interestComponent,
                            emi,
                            outstanding
                    )
            );
        }

        return schedules;
    }
}




