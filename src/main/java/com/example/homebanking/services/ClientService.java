package com.example.homebanking.services;

import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAllClient();
    List<ClientDTO> getAllClientDTO();

    Client saveClient(String firstName, String lastName, String email, String password);

    Client findById(long id);

    Client findByEmail(String email);

    ClientDTO getClientDTO(long id);
}
