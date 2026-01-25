package com.crlm.repository;

import com.crlm.model.Customer;
import com.crlm.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>{


    Optional<Customer> findByPan(String pan);

    Optional<Customer> findByUserAccount(UserAccount userAccount);

    boolean existsByPan(String pan);

    boolean existsByUserAccount(UserAccount userAccount);


}
