//package com.crlm.repositorytest;
//
//import com.crlm.model.LoanAccount;
//import com.crlm.repository.LoanAccountRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@DataJpaTest
//class LoanAccountRepositoryTest {
//
//    @Autowired
//    private LoanAccountRepository repository;
//
//    @Test
//    void shouldSaveAndFindByDecisionId() {
//        LoanAccount account = /* create valid LoanAccount */;
//
//        repository.save(account);
//
//        Optional<LoanAccount> found =
//                repository.findByLoanDecisionId(account.getLoanDecisionId());
//
//        assertTrue(found.isPresent());
//    }
//
//    @Test
//    void shouldReturnAllLoansForCustomer() {
//        UUID customerId = UUID.randomUUID();
//
//        // save 2 loan accounts for same customer
//
//        List<LoanAccount> accounts =
//                repository.findAllByCustomerId(customerId);
//
//        assertEquals(2, accounts.size());
//    }
//}
//
