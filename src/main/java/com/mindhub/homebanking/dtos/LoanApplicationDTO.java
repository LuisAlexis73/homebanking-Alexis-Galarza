package com.mindhub.homebanking.dtos;

import java.util.List;

public class LoanApplicationDTO {

    private long id;
    private double amount;
    private Integer payments;
    private String accountDestiny;

    public LoanApplicationDTO() {}

    public LoanApplicationDTO(long id, double amount, Integer payments, String accountDestiny) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.accountDestiny = accountDestiny;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public String getAccountDestiny() {
        return accountDestiny;
    }

    public void setAccountDestiny(String accountDestiny) {
        this.accountDestiny = accountDestiny;
    }
}
