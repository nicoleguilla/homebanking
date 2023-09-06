package com.example.homebanking.services;

import com.example.homebanking.dtos.CardDTO;
import com.example.homebanking.models.Card;
import com.example.homebanking.models.CardColor;
import com.example.homebanking.models.CardType;

import java.util.List;

public interface CardService {
    void save(Card card);
    List<Card> findAll();
    List<CardDTO> getAllCardDTO();

    List<CardDTO> getCurrentClientCards(String email);

    Card createCurrentCard(String firstName, String lastName, String cardType, String cardColor);

    List<Card> getFilteredCards(String email, CardType cardType);

}
