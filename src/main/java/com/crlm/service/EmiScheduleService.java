package com.crlm.service;

import com.crlm.model.EmiSchedule;

import java.util.List;
import java.util.UUID;

public interface EmiScheduleService {

    List<EmiSchedule> getScheduleForLoanAccount(UUID loanAccountId);
}

