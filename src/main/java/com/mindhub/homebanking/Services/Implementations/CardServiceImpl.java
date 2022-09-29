package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CardRepository cardRepository;

    @Override
    public void saveCard(Card card){
        cardRepository.save(card);
    }

    @Override
    public Card getCardById(long id){return cardRepository.findById(id).get();}

}
