package com.example.homebanking.services;

import com.example.homebanking.dtos.TransactionDTO;
import com.example.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {
    void save(Transaction transaction);

    List<TransactionDTO> getTransactionsDTO();

    TransactionDTO getTransactionDTO(Long id);
}
