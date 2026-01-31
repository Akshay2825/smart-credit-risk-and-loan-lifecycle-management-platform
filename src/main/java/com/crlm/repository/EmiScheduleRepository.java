package com.crlm.repository;

import com.crlm.model.EmiSchedule;
import com.crlm.enums.EmiStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmiScheduleRepository extends JpaRepository<EmiSchedule, UUID> {

    List<EmiSchedule> findByLoanAccountIdOrderByInstallmentNumber(UUID loanAccountId);

    List<EmiSchedule> findByLoanAccountIdAndStatus(
            UUID loanAccountId,
            EmiStatus status
    );
}

