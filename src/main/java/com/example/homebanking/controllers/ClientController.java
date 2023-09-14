package com.example.homebanking.controllers;

import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.services.AccountService;
import com.example.homebanking.services.implement.ClientServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ClientController {

    @Autowired
    private ClientServiceImplement clientService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDTO>> getClients() {
        return new ResponseEntity<>(clientService.getAllClientDTO(), HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.getClientDTO(id), HttpStatus.OK);
    }

    @GetMapping("/clients/current")
    public ClientDTO getClientCurrent(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        return new ClientDTO(client);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isBlank()) {
            return new ResponseEntity<>("Missing first name", HttpStatus.FORBIDDEN);
        }
        if (lastName.isBlank()) {
            return new ResponseEntity<>("Missing last name", HttpStatus.FORBIDDEN);
        }
        if (email.isBlank()) {
            return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);
        }
        if (password.isBlank()) {
            return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        Client client = clientService.saveClient(firstName, lastName, email, password);

        Account account = accountService.createAccount();
        account.setClient(client);
        accountService.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}