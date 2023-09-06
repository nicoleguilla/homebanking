package com.example.homebanking.repositories;

import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.services.AccountService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByClientId(long client_id);
    List<Account> findByClientEmail(String email);

    Optional<Account> findByNumber(String number);
    boolean existsByNumber(String number);
}
