package com.mindhub.homebanking.dtos;

import java.time.LocalDateTime;

public class FilterTransactionsDTO {
    public LocalDateTime fromDate, toDate;
    public String accountNumber;

    public FilterTransactionsDTO() { }

    public FilterTransactionsDTO(LocalDateTime fromDate, LocalDateTime toDate, String accountNumber) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
