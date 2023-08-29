package com.example.homebanking.controllers;

import com.example.homebanking.dtos.AccountDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAccounts() {
        List<AccountDTO> accountDTOS = accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(accountDTOS, HttpStatus.OK);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getClient(@PathVariable Long id) {
        AccountDTO accountDTO = accountRepository.findById(id).map(AccountDTO::new).orElse(null);
        System.out.println(accountDTO);
        return new ResponseEntity<>(accountDTO,HttpStatus.OK);
    }


    @RequestMapping(path ="/clients/current/accounts" ,method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication){

        if(accountRepository.findByClientEmail(authentication.getName()).size()>=3){
            return new ResponseEntity<>("User has 3 accounts", HttpStatus.FORBIDDEN);
        }

        Account account = new Account(createNumberAccount(), LocalDate.now(),0.0);
        account.setClient(clientRepository.findByEmail(authentication.getName()));
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    protected String createNumberAccount(){
        String numberAccount;
        do {
            int number = (int) (Math.random() * 99999999);
            numberAccount = "VIN-" + number;
        } while (accountRepository.existsByNumber(numberAccount));
        return numberAccount;
    }

    @RequestMapping(path ="/clients/current/accounts")
    public List<AccountDTO> getClientAccount(Authentication authentication){
        return accountRepository.findByClientEmail(authentication.getName()).stream().map(AccountDTO::new).collect(Collectors.toList());
    }
}