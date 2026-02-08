package com.crlm.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class EmiPaymentRequestDTO {

    @NotNull
    private UUID emiScheduleId;

    @NotNull
    @DecimalMin("1.00")
    private BigDecimal amountPaid;

    @NotNull
    @PastOrPresent
    private LocalDate paymentDate;


}
