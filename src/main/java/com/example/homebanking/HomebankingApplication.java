package com.example.homebanking;

import com.example.homebanking.models.*;
import com.example.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository) {
		return (args) -> {

			Client client1 = new Client("Melba", "Morel","melba@mindhub.com");
			Client client2 = new Client("Nicole", "Guillamon","nisdkjslda@gmail.com");

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
			ClientLoan clientLoan1 = new ClientLoan(loan1.getName(),400000,loan1.getPayments().get(4));
			ClientLoan clientLoan2 = new ClientLoan(loan2.getName(),50000,loan2.getPayments().get(1));
			ClientLoan clientLoan3 = new ClientLoan(loan2.getName(),100000,loan2.getPayments().get(2));
			ClientLoan clientLoan4 = new ClientLoan(loan3.getName(),200000,loan3.getPayments().get(3));


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
		};
	}



}
