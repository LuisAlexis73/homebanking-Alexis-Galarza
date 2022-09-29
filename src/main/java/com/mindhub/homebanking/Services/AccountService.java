package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {

    public List<Account> getAccount();

    public Account getAccountById(long id);

    public Account getAccountByNumber(String number);

    public void saveAccount(Account account);

    public void removeAccount(Account account);

}
