package com.crlm.repository;

import com.crlm.model.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanAccountRepository extends JpaRepository<LoanAccount, UUID> {

    /**
     * Fetch loan account using loan decision id.
     * One decision -> one loan account.
     */
    Optional<LoanAccount> findByLoanDecisionId(UUID loanDecisionId);

    /**
     * Check if loan account already exists for a decision.
     * Prevents duplicate loan accounts.
     */
    boolean existsByLoanDecisionId(UUID loanDecisionId);

    /**
     * Fetch all loan accounts for a customer.
     * Used for dashboards, statements, reporting.
     */
    List<LoanAccount> findAllByCustomerId(UUID customerId);
}

