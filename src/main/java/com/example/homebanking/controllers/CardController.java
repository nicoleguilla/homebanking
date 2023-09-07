package com.example.homebanking.controllers;

import com.example.homebanking.dtos.CardDTO;
import com.example.homebanking.models.Card;
import com.example.homebanking.models.CardColor;
import com.example.homebanking.models.CardType;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.CardRepository;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.services.CardService;
import com.example.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @RequestMapping("/cards")
    public List<CardDTO> getAllCards(){
        return cardService.getAllCardDTO();
    }

    @RequestMapping("/clients/current/cards")
    public List<CardDTO> getCurrentClientCards(Authentication authentication){
        return cardService.getCurrentClientCards(authentication.getName());
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCurrentCard(@RequestParam String cardType, @RequestParam String cardColor, Authentication authentication){

        if(cardType.isEmpty()) {
            return new ResponseEntity<>("Missing card type", HttpStatus.FORBIDDEN);
        }
        if(cardColor.isEmpty()) {
            return new ResponseEntity<>("Missing card color", HttpStatus.FORBIDDEN);
        }
        List<Card> debitCards = cardService.getFilteredCards(authentication.getName(), CardType.DEBIT);
        List<Card> creditCards = cardService.getFilteredCards(authentication.getName(), CardType.CREDIT);
        if(debitCards.size() >= 3 || creditCards.size() >= 3){
            if(debitCards.size() >= 3 && CardType.valueOf(cardType).equals(CardType.DEBIT))
                return new ResponseEntity<>("User has already 3 debit cards", HttpStatus.FORBIDDEN);
            if(creditCards.size() >= 3 && CardType.valueOf(cardType).equals(CardType.CREDIT))
                return new ResponseEntity<>("User has already 3 credit cards", HttpStatus.FORBIDDEN);
        }

        Client authenticatedClient = clientService.findByEmail(authentication.getName());

        Card card = cardService.createCurrentCard(authenticatedClient.getFirstName(), authenticatedClient.getLastName(), cardType, cardColor);
        card.setClient(authenticatedClient);
        cardService.save(card);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
