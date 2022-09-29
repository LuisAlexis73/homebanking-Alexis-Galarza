package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    @Override
    public Set<Transaction> filterTransactionsWithDate(LocalDateTime fromDate, LocalDateTime toDate, Account account){
        return transactionRepository.findByDateBetween(fromDate, toDate).stream().filter(transaction -> transaction.getAccount() == account).collect(Collectors.toSet());
    }

}
