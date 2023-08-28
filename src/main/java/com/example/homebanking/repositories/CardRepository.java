package com.example.homebanking.repositories;

import com.example.homebanking.models.Account;
import com.example.homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepository extends JpaRepository <Card, Long> {
    List<Card> findByClientEmail(String email);
}
