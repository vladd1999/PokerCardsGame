package com.backend.model;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private int suit;
    private int rank;

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Card() {
    }
    public static List<Card> copyOf(List<Card> cards){
        List<Card> cardList = new ArrayList<>();
        for(Card card:cards){
            cardList.add(new Card(card.getSuit(),card.getRank()));
        }
        return  cardList;
    }
    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
