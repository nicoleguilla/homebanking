package com.example.homebanking.controllers;

import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/clients")
    public ResponseEntity<List<ClientDTO>> getClients() {
        List<ClientDTO> clients = clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        ClientDTO clientDTO = clientRepository.findById(id).map(ClientDTO::new).orElse(null);
        return new ResponseEntity<>(clientDTO,HttpStatus.OK);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getClientCurrent(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(client);
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
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
        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);

        Account account = new Account("VIN-"+(Math.random() * 99999999), LocalDate.now(),0.0);
        account.setClient(client);
        accountRepository.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}