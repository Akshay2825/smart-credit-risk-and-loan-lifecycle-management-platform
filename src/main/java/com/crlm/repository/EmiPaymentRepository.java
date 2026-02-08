package com.crlm.repository;

import com.crlm.model.EmiPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface EmiPaymentRepository extends JpaRepository<EmiPayment, UUID> {

    List<EmiPayment> findByEmiScheduleId(UUID emiScheduleId);

    @Query("""
        select coalesce(sum(p.amountPaid), 0)
        from EmiPayment p
        where p.emiScheduleId = :emiScheduleId
    """)
    BigDecimal totalPaidForEmi(UUID emiScheduleId);
}

