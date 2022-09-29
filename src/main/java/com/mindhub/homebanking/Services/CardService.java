package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Card;

public interface CardService {
    public void saveCard(Card card);

    public Card getCardById(long id);
}
