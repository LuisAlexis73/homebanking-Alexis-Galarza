package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    public Loan getLoanById(long id);

    public Loan getLoanByName(String name);

    public List<Loan> getLoans();

    public void saveLoan(Loan loan);

}
