package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class HomeBankingApplication {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(HomeBankingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
                                      TransactionRepository transactionRepository,
                                      LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,
                                      CardRepository cardRepository) {
        return (args) -> {
            Client client1 = new Client("Melba", "Morel", "melba@mindhub.com",
                    passwordEncoder.encode("melba4321"));

            Client client2 = new Client("Alex", "Galar", "galax@gmail.com",
                    passwordEncoder.encode("sub3435"));

            Client admin = new Client("Diego Armando", "Maradona", "admin@gmail.com",
                    passwordEncoder.encode("contraseña10"));

            Account account1 = new Account("VIN001", LocalDateTime.now(), 5000, client1, AccountType.CURRENT, true);

            Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500, AccountType.SAVING, true);
            client1.addAccount(account2);

            Account account4 = new Account("VIN003", LocalDateTime.now().plusDays(1),
                    50000.0, client2, AccountType.SAVING, true);

            Card card1 = new Card(client1, client1.getFirstName() + " " + client1.getLastName(),
                    "4517-6479-0645-2000", 633, LocalDate.now(),
                    LocalDate.now().plusYears(5), CardType.DEBIT, CardColor.GOLD, true);

            Card card2 = new Card(client1, client1.getFirstName() + " " + client1.getLastName(),
                    "1243-2054-6488-7555", 240, LocalDate.now(),
                    LocalDate.now().plusYears(5), CardType.CREDIT, CardColor.TITANIUM, true);

            Card card3 = new Card(client2, client2.getFirstName() + " " + client2.getLastName(),
                    "4585-1828-5974-5666", 100, LocalDate.now(),
                    LocalDate.now().plusYears(5), CardType.CREDIT, CardColor.SILVER, true);

            Transaction transaction1 = new Transaction(TransactionType.DEBIT, -2600.00,
                    "Primer transaccion", LocalDateTime.now(), account1);

            Transaction transaction2 = new Transaction(TransactionType.CREDIT, 4500.00,
                    "Segunda transaccion", LocalDateTime.now(),account1);

            Transaction transaction3 = new Transaction(TransactionType.CREDIT, 45000.00,
                    "Primer transaccion", LocalDateTime.now(),account4);

            Loan loan1 = new Loan(List.of(12, 24, 36, 48, 60), "Charioteer", 500000.00);

            Loan loan2 = new Loan(List.of(6, 12, 24), "Personal", 100000.00);

            Loan loan3 = new Loan(List.of(6, 12, 24, 36), "Automotive", 300000.00);

            ClientLoan prestamo1 = new ClientLoan(client1, loan1, 400000.00, 60);

            ClientLoan prestamo2 = new ClientLoan(client1, loan2, 50000.00, 12);

            ClientLoan prestamo3 = new ClientLoan(client2, loan2, 100000.00, 24);

            ClientLoan prestamo4 = new ClientLoan(client2, loan3, 200000.00, 36);

            clientRepository.save(client1);
            accountRepository.save(account1);
            accountRepository.save(account2);

            clientRepository.save(client2);
            accountRepository.save(account4);

            clientRepository.save(admin);

            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);
            transactionRepository.save(transaction3);

            loanRepository.save(loan1);
            loanRepository.save(loan2);
            loanRepository.save(loan3);

            clientLoanRepository.save(prestamo1);
            clientLoanRepository.save(prestamo2);

            clientLoanRepository.save(prestamo3);
            clientLoanRepository.save(prestamo4);

            cardRepository.save(card1);
            cardRepository.save(card2);
            cardRepository.save(card3);
        };
    }
}
