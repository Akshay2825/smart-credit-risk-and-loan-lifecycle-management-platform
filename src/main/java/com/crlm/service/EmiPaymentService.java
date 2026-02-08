package com.crlm.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface EmiPaymentService {

    void recordPayment(
            UUID emiScheduleId,
            BigDecimal amountPaid,
            LocalDate paymentDate
    );
}
