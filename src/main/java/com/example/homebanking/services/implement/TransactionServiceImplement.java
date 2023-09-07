package com.example.homebanking.services.implement;

import com.example.homebanking.dtos.TransactionDTO;
import com.example.homebanking.models.Transaction;
import com.example.homebanking.models.TransactionType;
import com.example.homebanking.repositories.TransactionRepository;
import com.example.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> getTransactionsDTO() {
        return  transactionRepository.findAll().stream().map(TransactionDTO::new).collect(Collectors.toList());
    }

    @Override
    public TransactionDTO getTransactionDTO(Long id) {
        return transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
    }

    @Override
    public Transaction createCreditTransaction(double amount, String description) {
        return new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now());
    }

    @Override
    public Transaction createDebitTransaction(double amount, String description) {
        return new Transaction(TransactionType.DEBIT, -amount, description, LocalDateTime.now());
    }
}
