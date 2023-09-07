package com.example.homebanking.services;

import com.example.homebanking.dtos.TransactionDTO;
import com.example.homebanking.models.Transaction;
import com.example.homebanking.models.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    void save(Transaction transaction);

    List<TransactionDTO> getTransactionsDTO();

    TransactionDTO getTransactionDTO(Long id);

    Transaction createCreditTransaction(double amount, String description);

    Transaction createDebitTransaction(double amount, String description);

}
