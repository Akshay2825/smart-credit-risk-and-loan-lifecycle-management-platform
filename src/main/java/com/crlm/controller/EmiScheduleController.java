package com.crlm.controller;

import com.crlm.dto.EmiScheduleResponseDTO;
import com.crlm.model.EmiSchedule;
import com.crlm.service.EmiScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/loan-accounts")
public class EmiScheduleController {

    private final EmiScheduleService emiScheduleService;

    public EmiScheduleController(EmiScheduleService emiScheduleService) {
        this.emiScheduleService = emiScheduleService;
    }

    @GetMapping("/{loanAccountId}/emis")
    public List<EmiScheduleResponseDTO> getEmiSchedule(
            @PathVariable UUID loanAccountId
    ) {
        return emiScheduleService.getScheduleForLoanAccount(loanAccountId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private EmiScheduleResponseDTO toResponse(EmiSchedule emi) {
        return new EmiScheduleResponseDTO(
                emi.getInstallmentNumber(),
                emi.getDueDate(),
                emi.getPrincipalComponent(),
                emi.getInterestComponent(),
                emi.getEmiAmount(),
                emi.getOutstandingBalance(),
                emi.getStatus()
        );
    }
}
