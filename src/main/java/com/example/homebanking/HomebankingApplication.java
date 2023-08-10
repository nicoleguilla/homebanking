package com.example.homebanking;

import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.models.Transaction;
import com.example.homebanking.models.TransactionType;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
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
		};
	}



}
