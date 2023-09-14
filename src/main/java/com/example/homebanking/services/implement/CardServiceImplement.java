package com.example.homebanking.services.implement;

import com.example.homebanking.dtos.CardDTO;
import com.example.homebanking.models.Card;
import com.example.homebanking.models.CardColor;
import com.example.homebanking.models.CardType;
import com.example.homebanking.repositories.CardRepository;
import com.example.homebanking.services.CardService;
import com.example.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public void save(Card card) {
        cardRepository.save(card);
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public List<CardDTO> getAllCardDTO() {
        return findAll().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<CardDTO> getCurrentClientCards(String email) {
        return cardRepository.findByClientEmail(email).stream().map(CardDTO::new).collect(toList());
    }

    @Override
    public Card createCurrentCard(String firstName, String lastName, String cardType, String cardColor) {
        String cardNumber = CardUtils.getCardNumber();
        int cvv = CardUtils.getCvv();
        return new Card(firstName + " " + lastName, CardType.valueOf(cardType), CardColor.valueOf(cardColor), cardNumber, cvv, LocalDateTime.now(), LocalDateTime.now().plusYears(5));
    }


    @Override
    public List<Card> getFilteredCards(String email, CardType cardType) {
        return cardRepository.findByClientEmail(email).stream().filter(card -> card.getType().equals(cardType)).collect(toList());
    }

}
