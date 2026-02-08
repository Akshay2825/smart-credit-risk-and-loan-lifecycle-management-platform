package com.crlm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(
        name = "emi_payments",
        indexes = {
                @Index(name = "idx_payment_emi", columnList = "emi_schedule_id"),
                @Index(name = "idx_payment_date", columnList = "payment_date")
        }
)
public class EmiPayment extends BaseEntity {

    @Column(name = "emi_schedule_id", nullable = false, updatable = false)
    private UUID emiScheduleId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amountPaid;

    @Column(nullable = false)
    private LocalDate paymentDate;   // business date

    @Column(nullable = false, updatable = false)
    private Instant receivedAt;       // system timestamp

    protected EmiPayment() {}

    private EmiPayment(
            UUID emiScheduleId,
            BigDecimal amountPaid,
            LocalDate paymentDate
    ) {
        this.emiScheduleId = emiScheduleId;
        this.amountPaid = amountPaid;
        this.paymentDate = paymentDate;
        this.receivedAt = Instant.now();
    }

    public static EmiPayment record(
            UUID emiScheduleId,
            BigDecimal amountPaid,
            LocalDate paymentDate
    ) {
        return new EmiPayment(emiScheduleId, amountPaid, paymentDate);
    }

}

