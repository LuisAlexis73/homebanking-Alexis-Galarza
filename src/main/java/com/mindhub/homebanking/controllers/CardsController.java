package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class CardsController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createdCards(
            @RequestParam String cardColor, @RequestParam String cardType, Authentication authentication){

        Client newAuthenticClient = clientService.getClientByEmail(authentication.getName());
        String cardNumber = getRandom(1000, 9999) + "-" + getRandom(1000, 9999) + "-" + getRandom(1000, 9999) + "-" + getRandom(1000, 9999);
        int cvv = getRandom(100, 999);

        if ( cardType.isEmpty() ){
            return new ResponseEntity<>("Missing card type", HttpStatus.FORBIDDEN);
        }
        if ( cardColor.isEmpty() ){
            return new ResponseEntity<>("Missing card color", HttpStatus.FORBIDDEN);
        }
        if(newAuthenticClient.getCards().stream().filter(card -> card.getColor().toString().equals(cardColor) && card.getType().toString().equals(cardType)).count() >= 3){
            return new ResponseEntity<>("You cannot create cards with same cardColor and cardType", HttpStatus.FORBIDDEN);
        }

        cardService.saveCard(new Card(newAuthenticClient, newAuthenticClient.toString(), cardNumber, cvv, LocalDate.now(),
                LocalDate.now().plusYears(5), CardType.valueOf(cardType), CardColor.valueOf(cardColor), true));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/cards/remove")
    public ResponseEntity<Object> removeCard(Authentication authentication, @RequestParam long idCard){

        Client clientAuthentic = clientService.getClientByEmail(authentication.getName());

        Card card = cardService.getCardById(idCard);

        if (card.getActive() == null){
            return new ResponseEntity<>("The card doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (!clientAuthentic.getCards().contains(card)){
            return new ResponseEntity<>("the card doesn't belong to an authenticated customer", HttpStatus.FORBIDDEN);
        }

        card.setActive(false);
        cardService.saveCard(card);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }



    public int getRandom(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
