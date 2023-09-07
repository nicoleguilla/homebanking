package com.example.homebanking.services.implement;

import com.example.homebanking.dtos.LoanDTO;
import com.example.homebanking.models.Loan;
import com.example.homebanking.repositories.LoanRepository;
import com.example.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public Loan findById(long id) {
        return loanRepository.findById(id).orElse(null);
    }
}
