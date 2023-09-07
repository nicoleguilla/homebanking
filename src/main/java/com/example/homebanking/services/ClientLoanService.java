package com.example.homebanking.services;

import com.example.homebanking.dtos.ClientLoanDTO;
import com.example.homebanking.models.ClientLoan;

import java.util.List;

public interface ClientLoanService {
    List<ClientLoanDTO> getClientLoans();
    ClientLoan findById(long id);
    void save(ClientLoan clientLoan);
    ClientLoan createClientLoan(double amount,int payments);
}
