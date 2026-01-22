package com.crlm.repository;

import com.crlm.model.LoanDecision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoanDecisionRepository extends JpaRepository<LoanDecision, UUID> {

    Optional<LoanDecision> findByApplicationId(UUID applicationId);

    boolean existsByApplicationId(UUID applicationId);
}
