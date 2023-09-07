package com.example.homebanking.services;

import com.example.homebanking.dtos.LoanDTO;
import com.example.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getLoans();
    Loan findById(long id);
}
