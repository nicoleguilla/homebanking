package com.example.homebanking;

import com.example.homebanking.models.*;
import com.example.homebanking.repositories.*;
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
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
		return (args) -> {
	/*
			Client client1 = new Client("Melba", "Morel","melba@mindhub.com", passwordEncoder.encode("asd123"));
			Client client2 = new Client("Nicole", "Guillamon","nisdkjslda@gmail.com", passwordEncoder.encode("qwe123"));

			clientRepository.save(client1);
			clientRepository.save(client2);

			LocalDate now = LocalDate.now();
			LocalDate nextDay = now.plusDays(1);
			Account account1 = new Account("VIN001",now,5000.0);
			Account account2 = new Account("VIN002",nextDay,7500.0);
			Account account3 = new Account("VIN003",LocalDate.now(),10000.0);

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

			clientRepository.save(client1);
			clientRepository.save(client2);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,3000.0,"Pago", LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,-2000.0,"Retiro",LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.DEBIT,-1000.0,"Retiro",LocalDateTime.now());

			account1.addTransaction(transaction1);
			account2.addTransaction(transaction2);
			account3.addTransaction(transaction3);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

			Loan loan1 = new Loan("Mortgage", 500000, List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal", 100000, List.of(6,12,24));
			Loan loan3 = new Loan("Automotive", 300000, List.of(6,12,24,36));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);
			ClientLoan clientLoan1 = new ClientLoan(400000,loan1.getPayments().get(4));
			ClientLoan clientLoan2 = new ClientLoan(50000,loan2.getPayments().get(1));
			ClientLoan clientLoan3 = new ClientLoan(100000,loan2.getPayments().get(2));
			ClientLoan clientLoan4 = new ClientLoan(200000,loan3.getPayments().get(3));

			loan1.addClientLoan(clientLoan1);
			loan2.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan3);
			loan3.addClientLoan(clientLoan4);
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			client1.addClientLoan(clientLoan1);
			client1.addClientLoan(clientLoan2);
			client2.addClientLoan(clientLoan3);
			client2.addClientLoan(clientLoan4);
			clientRepository.save(client1);
			clientRepository.save(client2);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.DEBIT, CardColor.GOLD,"3489 3442 3243 2342",453,LocalDateTime.now(),LocalDateTime.now().plusYears(5));
			Card card2 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.CREDIT, CardColor.TITANIUM,"3246 8768 4564 9877",754,LocalDateTime.now(),LocalDateTime.now().plusYears(5));
			Card card3 = new Card(client2.getFirstName()+" "+client2.getLastName(),CardType.CREDIT, CardColor.SILVER,"2345 6564 8768 3454",237,LocalDateTime.now(),LocalDateTime.now().plusYears(5));

			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);
			clientRepository.save(client1);
			clientRepository.save(client2);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);*/
		};
	}



}
