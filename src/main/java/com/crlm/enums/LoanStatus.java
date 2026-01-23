package com.crlm.enums;

public enum LoanStatus {

    /**
     * Loan is active and EMIs are pending.
     */
    ACTIVE,

    /**
     * All EMIs are paid successfully.
     */
    CLOSED,

    /**
     * Loan has overdue EMIs beyond threshold.
     */
    DEFAULTED
}
