package com.example.homebanking.controllers;

import com.example.homebanking.dtos.TransactionDTO;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactions() {
        List<TransactionDTO> transactions= transactionRepository.findAll().stream().map(TransactionDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        TransactionDTO transactionDTO = transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
        return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
    }
}