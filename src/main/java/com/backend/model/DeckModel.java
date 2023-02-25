package com.backend.model;

import com.backend.utils.Rank;
import com.backend.utils.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;


public class DeckModel {

    private ArrayList<CardModel> deck;

    public DeckModel() {
        newDeck();
        shuffle();
    }

    public ArrayList<CardModel> getDeck() {
        return deck;
    }

    public void add(CardModel cardModel) {
        deck.add(cardModel);
    }

    public CardModel get(int index) {
        return deck.get(index);
    }

    public CardModel remove(int index) {
        return deck.remove(index);
    }


    public void newDeck() {
        CardModel cardModel;

        ArrayList<CardModel> deck = new ArrayList<CardModel>();

        ArrayList<Suit> suits =
                new ArrayList<Suit>(EnumSet.allOf(Suit.class));

        ArrayList<Rank> cardTypes =
                new ArrayList<Rank>(EnumSet.allOf(Rank.class));

        for(Suit suit : suits) {
            for (Rank value : cardTypes) {
                cardModel = new CardModel(suit, value);
                deck.add(cardModel);
            }
        }
        this.deck = deck;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }



}
