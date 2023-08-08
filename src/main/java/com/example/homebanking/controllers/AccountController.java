package com.example.homebanking.controllers;

import com.example.homebanking.dtos.AccountDTO;
import com.example.homebanking.repositories.AccountRepository;
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
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    ResponseEntity<List<AccountDTO>> getAccounts() {
        List<AccountDTO> accountDTOS = accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(accountDTOS, HttpStatus.OK);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getClient(@PathVariable Long id) {
        AccountDTO accountDTO = accountRepository.findById(id).map(AccountDTO::new).orElse(null);
        System.out.println(accountDTO);
        return new ResponseEntity<>(accountDTO,HttpStatus.OK);
    }
}