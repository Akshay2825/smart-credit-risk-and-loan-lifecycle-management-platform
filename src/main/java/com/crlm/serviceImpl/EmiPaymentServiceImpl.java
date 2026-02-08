package com.crlm.serviceImpl;

import com.crlm.enums.EmiStatus;
import com.crlm.exception.BusinessRuleViolationException;
import com.crlm.exception.ResourceNotFoundException;
import com.crlm.model.EmiPayment;
import com.crlm.model.EmiSchedule;
import com.crlm.repository.EmiPaymentRepository;
import com.crlm.repository.EmiScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.crlm.service.EmiPaymentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class EmiPaymentServiceImpl implements EmiPaymentService {

    private final EmiScheduleRepository emiScheduleRepository;
    private final EmiPaymentRepository emiPaymentRepository;

    public EmiPaymentServiceImpl(
            EmiScheduleRepository emiScheduleRepository,
            EmiPaymentRepository emiPaymentRepository
    ) {
        this.emiScheduleRepository = emiScheduleRepository;
        this.emiPaymentRepository = emiPaymentRepository;
    }

    @Override
    public void recordPayment(
            UUID emiScheduleId,
            BigDecimal amountPaid,
            LocalDate paymentDate
    ) {

        EmiSchedule emi =
                emiScheduleRepository.findById(emiScheduleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "EMI not found: " + emiScheduleId
                                )
                        );

        if (emi.getStatus() == EmiStatus.PAID) {
            throw new BusinessRuleViolationException(
                    "EMI already fully paid"
            );
        }

        if (paymentDate.isBefore(emi.getDueDate())) {
            throw new BusinessRuleViolationException(
                    "Payment date cannot be before EMI due date"
            );
        }

        BigDecimal alreadyPaid =
                emiPaymentRepository.totalPaidForEmi(emiScheduleId);

        BigDecimal totalAfterPayment =
                alreadyPaid.add(amountPaid);

        if (totalAfterPayment.compareTo(emi.getEmiAmount()) > 0) {
            throw new BusinessRuleViolationException(
                    "Overpayment not allowed for EMI"
            );
        }

        EmiPayment payment =
                EmiPayment.record(
                        emiScheduleId,
                        amountPaid,
                        paymentDate
                );

        emiPaymentRepository.save(payment);

        if (totalAfterPayment.compareTo(emi.getEmiAmount()) == 0) {
            emi.markPaid();
        }
    }

}
