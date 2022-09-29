package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.Utilities.CreatePDF;
import com.mindhub.homebanking.dtos.FilterTransactionsDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@RestController
@RequestMapping("/api")
public class TransactionsController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> newTransaction(
            @RequestParam Double amount, @RequestParam String description, @RequestParam String accountOrigin,
            @RequestParam String accountDestiny, Authentication authentication){

        Client newTransactionClient = clientService.getClientByEmail(authentication.getName());
        Account typeOriginAccount = accountService.getAccountByNumber(accountOrigin);
        Account typeDestinyAccount = accountService.getAccountByNumber(accountDestiny);

        if (description.isEmpty() || accountOrigin.isEmpty() || accountDestiny.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0 ){
            return new ResponseEntity<>("You can't transfer negative amounts", HttpStatus.FORBIDDEN);
        }
        if (accountOrigin.equals(accountDestiny)){
            return new ResponseEntity<>("Origin account already in use", HttpStatus.FORBIDDEN);
        }
        if (typeOriginAccount == null){
            return new ResponseEntity<>("Origin account not exist.", HttpStatus.FORBIDDEN);
        }
        if (typeDestinyAccount == null){
            return new ResponseEntity<>("The account destiny not exist", HttpStatus.FORBIDDEN);
        }
        if (accountService.getAccountByNumber(accountOrigin).getBalance() < amount){
            return new ResponseEntity<>("Your current amount is not enough ", HttpStatus.FORBIDDEN);
        }

        Transaction newTransactionDebit = new Transaction(DEBIT, -amount , description + " go to " + accountDestiny, LocalDateTime.now(), typeOriginAccount);
        Transaction newTransactionCredit = new Transaction(CREDIT, amount , description + " from " + accountOrigin, LocalDateTime.now(), typeDestinyAccount);

        transactionService.saveTransaction(newTransactionDebit);
        transactionService.saveTransaction(newTransactionCredit);

        typeOriginAccount.setBalance(typeOriginAccount.getBalance() - amount);
        typeDestinyAccount.setBalance(typeDestinyAccount.getBalance() + amount);

        accountService.saveAccount(typeOriginAccount);
        accountService.saveAccount(typeDestinyAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/transactions/filtered")
    public ResponseEntity<Object> getFilteredTransaction(
            @RequestBody FilterTransactionsDTO filterTransactionsDTO, Authentication authentication) throws Exception {
        Client client = clientService.getClientByEmail(authentication.getName());

        Account account = accountService.getAccountByNumber(filterTransactionsDTO.getAccountNumber());

        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("You cannot request data from an account that isn't yours.", HttpStatus.FORBIDDEN);
        }
        if (account.getTransaction()==null){
            return new ResponseEntity<>("You don't have any transactions in this account.", HttpStatus.FORBIDDEN);
        }

        Set<Transaction> transactions = transactionService.filterTransactionsWithDate(filterTransactionsDTO.fromDate, filterTransactionsDTO.toDate, account);

        CreatePDF.createPDF(transactions);

        return new ResponseEntity<>("transactions",HttpStatus.CREATED);
    }

}
