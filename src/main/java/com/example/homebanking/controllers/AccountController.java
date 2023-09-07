package com.example.homebanking.controllers;

import com.example.homebanking.dtos.AccountDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.services.AccountService;
import com.example.homebanking.services.ClientService;
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
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAccounts() {
        List<AccountDTO> accountDTOS = accountService.getAllAccountDTO();
        return new ResponseEntity<>(accountDTOS, HttpStatus.OK);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getClient(@PathVariable Long id, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        if (account == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.FORBIDDEN);
        }
        if (account.getClient().getId().equals(client.getId())) {
            return new ResponseEntity<>(new AccountDTO(account), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Access deny", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){

        if(accountService.findByClientEmail(authentication.getName()).size()>=3){
            return new ResponseEntity<>("User has 3 accounts", HttpStatus.FORBIDDEN);
        }

        Account account = accountService.createAccount();
        account.setClient(clientService.findByEmail(authentication.getName()));
        accountService.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path ="/clients/current/accounts")
    public List<AccountDTO> getClientAccounts(Authentication authentication){
        return accountService.getClientAccounts(authentication.getName());
    }
}