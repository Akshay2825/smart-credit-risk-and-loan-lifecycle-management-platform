package com.crlm.repositorytest;

import com.crlm.model.LoanDecision;
import com.crlm.repository.LoanDecisionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoanDecisionRepositoryTest {

    @Autowired
    private LoanDecisionRepository repository;

    @Test
    void shouldSaveApprovedDecision() {
        LoanDecision decision =
                LoanDecision.autoApproved(
                        UUID.randomUUID(),
                        new BigDecimal("9.5"),
                        "Auto approved"
                );

        LoanDecision saved = repository.save(decision);

        assertNotNull(saved.getApplicationId());
    }
}

