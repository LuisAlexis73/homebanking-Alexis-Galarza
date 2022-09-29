package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;


public class ClientLoanDTO {

    private long id;
    private double amount;
    private Integer payments;
    private long loanId;
    private String name;

    public ClientLoanDTO(){}

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.payments = clientLoan.getPayments();
        this.amount = clientLoan.getAmount();
        this.name = clientLoan.getLoan().getName();
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }
}
