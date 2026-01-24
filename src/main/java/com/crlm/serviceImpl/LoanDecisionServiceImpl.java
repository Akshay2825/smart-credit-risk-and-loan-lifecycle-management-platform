package com.crlm.serviceImpl;

import com.crlm.enums.Decision;
import com.crlm.enums.DecisionBy;
import com.crlm.model.LoanDecision;
import com.crlm.repository.LoanDecisionRepository;
import com.crlm.service.LoanDecisionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class LoanDecisionServiceImpl implements LoanDecisionService {

    private final LoanDecisionRepository loanDecisionRepository;

    public LoanDecisionServiceImpl(LoanDecisionRepository loanDecisionRepository) {
        this.loanDecisionRepository = loanDecisionRepository;
    }

    /**
     * Auto approval by system rules.
     */
    @Override
    @Transactional
    public LoanDecision autoApprove(
            UUID applicationId,
            BigDecimal interestRate,
            String remarks
    ) {

        ensureDecisionDoesNotExist(applicationId);

        LoanDecision decision =
                LoanDecision.autoApproved(
                        applicationId,
                        interestRate,
                        remarks
                );

        return loanDecisionRepository.save(decision);
    }

    /**
     * Manual approval by loan officer.
     */
    @Override
    @Transactional
    public LoanDecision manualApprove(
            UUID applicationId,
            BigDecimal interestRate,
            String remarks
    ) {

        ensureDecisionDoesNotExist(applicationId);

        LoanDecision decision =
                LoanDecision.manuallyApproved(
                        applicationId,
                        interestRate,
                        remarks
                );

        return loanDecisionRepository.save(decision);
    }

    /**
     * Loan rejection (system or officer).
     */
    @Override
    @Transactional
    public LoanDecision reject(
            UUID applicationId,
            String remarks
    ) {

        ensureDecisionDoesNotExist(applicationId);

        LoanDecision decision =
                LoanDecision.rejected(
                        applicationId,
                        DecisionBy.LOAN_OFFICER,
                        remarks
                );

        return loanDecisionRepository.save(decision);
    }

    /**
     * Fetch decision by application id.
     */
    @Override
    public LoanDecision getByApplicationId(UUID applicationId) {
        return loanDecisionRepository.findByApplicationId(applicationId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "LoanDecision not found for applicationId: " + applicationId
                        )
                );
    }

    private void ensureDecisionDoesNotExist(UUID applicationId) {
        boolean exists =
                loanDecisionRepository.existsByApplicationId(applicationId);

        if (exists) {
            throw new IllegalStateException(
                    "LoanDecision already exists for applicationId: " + applicationId
            );
        }
    }
}
