package com.example.homebanking;

import com.example.homebanking.models.*;
import com.example.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }
    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Test
    public void existClient(){
        List<Client> clients=clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }
    @Test
    public void existMelbaClient(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("firstName", is("Melba"))));

    }

    @Test
    public void existAccount(){
        List<Account> accounts=accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }
    @Test
    public void existNumberAccountClient(){
        List<Account> accounts = accountRepository.findByClientId(1);
        assertThat(accounts, hasItem(hasProperty("number",is("VIN001"))));
    }

    @Test
    public void existCard(){
        List<Card> cards=cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }
    @Test
    public void hasGoldCard(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("color",is(CardColor.GOLD))));
    }

    @Test
    public void existTransaction(){
        List<Transaction> transactions=transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }
    @Test
    public void existCreditTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("type",is(TransactionType.CREDIT))));
    }

}



