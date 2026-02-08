package com.crlm.controller;

import com.crlm.dto.EmiPaymentRequestDTO;
import com.crlm.service.EmiPaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emi-payments")
public class EmiPaymentController {

    private final EmiPaymentService emiPaymentService;

    public EmiPaymentController(EmiPaymentService emiPaymentService) {
        this.emiPaymentService = emiPaymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void recordPayment(
            @Valid @RequestBody EmiPaymentRequestDTO request
    ) {
        emiPaymentService.recordPayment(
                request.getEmiScheduleId(),
                request.getAmountPaid(),
                request.getPaymentDate()
        );
    }
}
