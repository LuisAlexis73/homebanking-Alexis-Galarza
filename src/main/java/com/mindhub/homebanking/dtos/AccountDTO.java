package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private AccountType type;
    private boolean active;

    private Set<TransactionDTO> transaction;

    public AccountDTO(){ }

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transaction = account.getTransaction().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.type = account.getType();
        this.active = account.getActive();
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransaction() {
        return transaction;
    }

    public AccountType getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }
}
