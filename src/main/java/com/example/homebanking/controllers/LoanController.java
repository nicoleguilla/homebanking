package com.example.homebanking.controllers;

import com.example.homebanking.dtos.LoanApplicationDTO;
import com.example.homebanking.dtos.LoanDTO;
import com.example.homebanking.models.*;
import com.example.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {
    /*
    Verificar que los datos sean correctos, es decir no estén vacíos
    */

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientLoanService clientLoanService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoans();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoans(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());

        Loan loan = loanService.findById(loanApplicationDTO.getLoanId());

        Account destinationAccount = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required");
        }

        if (loanApplicationDTO == null) {
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() <= 0) {
            return new ResponseEntity<>("Choose an amount", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getPayments() <= 0) {
            return new ResponseEntity<>("Choose a payment", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getToAccountNumber().isEmpty()) {
            return new ResponseEntity<>("Put an account number", HttpStatus.FORBIDDEN);
        }
        if (loan == null) {
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }
        if (loan.getMaxAmount() < loanApplicationDTO.getAmount()) {
            return new ResponseEntity<>("Loan amount exceeds maximum", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("Invalid number of payments", HttpStatus.FORBIDDEN);
        }
        if (destinationAccount == null) {
            return new ResponseEntity<>("Destination account not found", HttpStatus.FORBIDDEN);
        }
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(destinationAccount)) {
            return new ResponseEntity<>("The account does not belong to the current client", HttpStatus.FORBIDDEN);
        }
        
        ClientLoan clientLoan = clientLoanService.createClientLoan(loanApplicationDTO.getAmount() * 1.2, loanApplicationDTO.getPayments());
        clientLoan.setClient(client);
        clientLoan.setLoan(loan);

        client.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);

        Transaction creditTransaction = transactionService.createCreditTransaction(loanApplicationDTO.getAmount(), loan.getName() + " loan approved");

        destinationAccount.addTransaction(creditTransaction);

        destinationAccount.setBalance(destinationAccount.getBalance() + clientLoan.getAmount());

        accountService.save(destinationAccount);
        transactionService.save(creditTransaction);
        clientLoanService.save(clientLoan);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}