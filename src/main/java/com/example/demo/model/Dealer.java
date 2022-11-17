package com.example.demo.model;
import com.example.demo.utils.Dealing;

import java.util.ArrayList;

public class Dealer implements Dealing {

    Deck deck;

    public Dealer() {
        this.deck = new Deck();
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Card getCard(int index) {
        return deck.get(index);
    }

    public Card drawCard(int index) {
        Card card = getCard(index);
        deck.remove(index);
        return card;
    }

    public void dealCard(Player player, Card card) {
        player.getHand().add(card);
    }

    public void deal(int cardsPerPlayer, ArrayList<Player> players) {
        for (Player player : players) {
            for (int i = 0; i < cardsPerPlayer; i++) {
                Card card = drawCard(0);
                dealCard(player, card);
            }
        }
    }

    public void dealTable(int numberOfCards, ArrayList<Card> table) {
        for (int i = 0; i < numberOfCards; i++) {
            Card card = drawCard(0);
            table.add(card);
        }
    }

}