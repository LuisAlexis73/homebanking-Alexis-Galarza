package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.LoanService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<String> addLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        Client currentClient = clientService.getClientByEmail(authentication.getName());

        Account accountDestination = accountService.getAccountByNumber(loanApplicationDTO.getAccountDestiny());

        Loan loan = loanService.getLoanById(loanApplicationDTO.getId());

        if (loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayments() <= 1 || loanApplicationDTO.getAccountDestiny().isEmpty()){

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (!loanService.getLoans().stream().map(loan1 -> loan.getId()).collect(toList()).contains(loanApplicationDTO.getId())){
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) {

            return new ResponseEntity<>("Amount exceeds maximum limit", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {

            return new ResponseEntity<>("Payments exceeds", HttpStatus.FORBIDDEN);
        }

        if (accountService.getAccountById(loanApplicationDTO.getId()) == null){

            return new ResponseEntity<>("This account not exist", HttpStatus.FORBIDDEN);
        }

        if (accountDestination == null){
            return new ResponseEntity<>("This account not exist", HttpStatus.FORBIDDEN);
        }

        if (currentClient.getClientLoans().stream().anyMatch(clientLoan -> clientLoan.getLoan() == loan)) {
            return new ResponseEntity<>("You already have an active "+ loan.getName(), HttpStatus.FORBIDDEN);
        }

        double plus = (loanApplicationDTO.getAmount() * 0.2) + loanApplicationDTO.getAmount();

        ClientLoan loanClient = new ClientLoan(currentClient, loan, plus, loanApplicationDTO.getPayments());
        clientLoanRepository.save(loanClient);

        switch (loan.getName()){
            case "Personal": if (loanClient.getPayments() == 6){
                loanClient.setAmount(loanClient.getAmount() * 1.2);
            }
            if (loanClient.getPayments() == 12){
                loanClient.setAmount(loanClient.getAmount() * 1.25);
            }
            if (loanClient.getPayments() == 24){
                loanClient.setAmount(loanClient.getAmount() * 1.3);
            }
                break;
            case "Charioteer": if (loanClient.getPayments() == 12){
                loanClient.setAmount(loanClient.getAmount() * 1.15);
            }
            if (loanClient.getPayments() == 24){
                loanClient.setAmount(loanClient.getAmount() * 1.2);
            }
            if (loanClient.getPayments() == 36){
                loanClient.setAmount(loanClient.getAmount() * 1.23);
            }
            if (loanClient.getPayments() == 48){
                loanClient.setAmount(loanClient.getAmount() * 1.25);
            }
            if (loanClient.getPayments() == 60){
                loanClient.setAmount(loanClient.getAmount() * 1.35);
            }
                break;
            case "Automotive": if (loanClient.getPayments() == 6){
                loanClient.setAmount(loanClient.getAmount() * 1.2);
            }
            if (loanClient.getPayments() == 12){
                loanClient.setAmount(loanClient.getAmount() * 1.3);
            }
            if (loanClient.getPayments() == 24){
                loanClient.setAmount(loanClient.getAmount() * 1.35);
            }
            if (loanClient.getPayments() == 36){
                loanClient.setAmount(loanClient.getAmount() * 1.4);
            }
                break;
        }

        Transaction transaction1 = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),
                loan.getName() + " Loan approved", LocalDateTime.now(), accountDestination);

        transactionService.saveTransaction(transaction1);
        accountDestination.setBalance(accountDestination.getBalance() + loanApplicationDTO.getAmount());


        return new ResponseEntity<>("Loan created success", HttpStatus.CREATED);

    }

    @GetMapping("/api/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getLoans().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }

    @PostMapping("/api/admin/loans")
    public ResponseEntity<Object> loansAdmin( @RequestParam String name,@RequestParam double maxAmount,
                                              @RequestParam List<Integer> payments , Authentication authentication){
        Client adminAuthentic = clientService.getClientByEmail(authentication.getName());

        if (adminAuthentic == null) {
            return new ResponseEntity<>("Missing admin authentication", HttpStatus.FORBIDDEN);
        }

        if (name.isEmpty()) {
            return new ResponseEntity<>("Missing name of loan", HttpStatus.FORBIDDEN);
        }

        if (maxAmount <= 0) {
            return new ResponseEntity<>("Invalid amount", HttpStatus.FORBIDDEN);
        }

        if (payments.isEmpty()) {
            return new ResponseEntity<>("Missing payments", HttpStatus.FORBIDDEN);
        }

        if (loanService.getLoans().stream().map(x -> x.getName()).collect(Collectors.toSet()).contains(name)){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        loanService.saveLoan(new Loan(payments, name, maxAmount));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
