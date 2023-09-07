package com.example.homebanking.services.implement;

import com.example.homebanking.dtos.ClientLoanDTO;
import com.example.homebanking.models.ClientLoan;
import com.example.homebanking.repositories.ClientLoanRepository;
import com.example.homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public List<ClientLoanDTO> getClientLoans() {
        return clientLoanRepository.findAll().stream().map(ClientLoanDTO::new).collect(toList());
    }

    @Override
    public ClientLoan findById(long id) {
        return clientLoanRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public ClientLoan createClientLoan(double amount, int payments) {
        return new ClientLoan(amount, payments);
    }
}
