package com.example.homebanking.services.implement;

import com.example.homebanking.dtos.AccountDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account createAccount() {
        return new Account(createNumberAccount(), LocalDate.now(), 0.0);
    }

    private String createNumberAccount(){
        String numberAccount;
        do {
            int number = (int) (Math.random() * 99999999);
            numberAccount = "VIN-" + number;
        } while (existsByNumber(numberAccount));
        return numberAccount;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<AccountDTO> getClientAccounts(String email) {
        return accountRepository.findByClientEmail(email).stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAllAccountDTO() {
        return findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<Account> findByClientId(long client_id) {
        return accountRepository.findByClientId(client_id);
    }

    @Override
    public List<Account> findByClientEmail(String email) {
        return accountRepository.findByClientEmail(email);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number).orElse(null);
    }

    @Override
    public boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }
}
