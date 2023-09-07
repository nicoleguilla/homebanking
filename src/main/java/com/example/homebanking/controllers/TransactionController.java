package com.example.homebanking.controllers;

import com.example.homebanking.dtos.TransactionDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.models.Transaction;
import com.example.homebanking.models.TransactionType;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.repositories.TransactionRepository;
import com.example.homebanking.services.AccountService;
import com.example.homebanking.services.ClientService;
import com.example.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountService accountService;

    @Autowired
    ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactions() {
        return new ResponseEntity<>(transactionService.getTransactionsDTO(), HttpStatus.OK);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        return new ResponseEntity<>(transactionService.getTransactionDTO(id), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication, @RequestParam double amount, @RequestParam String description, @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber){

        if (amount <= 0) {
            return new ResponseEntity<>("Amount invalid", HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty()) {
            return new ResponseEntity<>("Description is empty", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Number of source account is empty", HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Number of destination account is empty", HttpStatus.FORBIDDEN);
        }

        Account sourceAccount = accountService.findByNumber(fromAccountNumber);
        Account destinationAccount = accountService.findByNumber(toAccountNumber);

        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("Destination account number and source account number are the same", HttpStatus.FORBIDDEN);
        }

        if(sourceAccount==null){
            return new ResponseEntity<>("Source account not found",HttpStatus.FORBIDDEN);
        }

        if(!sourceAccount.getClient().getEmail().equals(authentication.getName())){
            return new ResponseEntity<>("Source account must be yours",HttpStatus.FORBIDDEN);
        }

        if(destinationAccount==null){
            return new ResponseEntity<>("Destination account not found",HttpStatus.FORBIDDEN);
        }

        if(sourceAccount.getBalance()<amount){
            return new ResponseEntity<>("Insufficient funds",HttpStatus.FORBIDDEN);
        }
        Transaction debitTransaction = transactionService.createDebitTransaction(amount, description + " (DEBIT" + fromAccountNumber +")");
        Transaction creditTransaction = transactionService.createCreditTransaction(amount, description + " (CREDIT " + toAccountNumber + ")");

        sourceAccount.addTransaction(debitTransaction);
        destinationAccount.addTransaction(creditTransaction);

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);

        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        transactionService.save(debitTransaction);
        transactionService.save(creditTransaction);
        accountService.save(sourceAccount);
        accountService.save(destinationAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}





