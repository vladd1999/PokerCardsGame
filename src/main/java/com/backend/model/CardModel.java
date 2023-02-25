package com.backend.model;

import com.backend.utils.Rank;
import com.backend.utils.Suit;


public class CardModel {

    private Suit suit;
    private Rank rank;


    public CardModel(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

}
