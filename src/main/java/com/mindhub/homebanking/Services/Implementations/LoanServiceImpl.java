package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.LoanService;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public Loan getLoanById(long id){
        return loanRepository.findById(id);
    }

    @Override
    public List<Loan> getLoans(){
        return loanRepository.findAll();
    }

    @Override
    public void saveLoan(Loan loan){loanRepository.save(loan);}

    @Override
    public Loan getLoanByName(String name){return loanRepository.findByName(name);}

}
