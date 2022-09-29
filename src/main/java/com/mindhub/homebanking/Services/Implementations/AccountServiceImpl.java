package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAccount(){return accountRepository.findAll();}

    @Override
    public Account getAccountById(long id){
        return accountRepository.findById(id).get();
    }

    @Override
    public Account getAccountByNumber(String number){
        return accountRepository.findByNumber(number);
    }

    @Override
    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    @Override
    public void removeAccount(Account account){accountRepository.delete(account);}
}
