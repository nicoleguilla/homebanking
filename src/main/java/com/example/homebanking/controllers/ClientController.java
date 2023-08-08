package com.example.homebanking.controllers;

import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients")
    public ResponseEntity<List<ClientDTO>> getClients() {
        List<ClientDTO> clients = clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
        System.out.println(clients);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        ClientDTO clientDTO = clientRepository.findById(id).map(ClientDTO::new).orElse(null);
        System.out.println(clientDTO);
        return new ResponseEntity<>(clientDTO,HttpStatus.OK);
    }

    @PostMapping("/clients")
    ResponseEntity<Client> saveClient(@RequestBody Client client) {
        Client client1 = clientRepository.save(client);
        return new ResponseEntity<>(client1, HttpStatus.CREATED);
    }
}
