package com.example.homebanking.services;

import com.example.homebanking.dtos.AccountDTO;
import com.example.homebanking.models.Account;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public interface AccountService {
    void save(Account account);

    Account createAccount();

    List<Account> findAll();
    Account findById(long id);

    List<AccountDTO> getClientAccounts(String email);

    List<AccountDTO> getAllAccountDTO();

    List<Account> findByClientId(long client_id);
    List<Account> findByClientEmail(String email);

    Account findByNumber(String number);
    boolean existsByNumber(String number);

}
