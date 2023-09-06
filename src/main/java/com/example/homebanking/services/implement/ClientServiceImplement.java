package com.example.homebanking.services.implement;

import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Client> findAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public List<ClientDTO> getAllClientDTO() {
        return findAllClient().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public Client saveClient(String firstName, String lastName, String email, String password) {
        return clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
    }

    @Override
    public Client findById(long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email).orElse(null);
    }

    @Override
    public ClientDTO getClientDTO(long id) {
        return new ClientDTO(findById(id));
    }
}
