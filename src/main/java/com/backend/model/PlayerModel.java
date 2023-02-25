package com.backend.model;

import java.util.ArrayList;

public class PlayerModel {

    private ArrayList<CardModel> hand = new ArrayList<>();
    private int id;
    private int cardNr = 1;
    private String name;

    public PlayerModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PlayerModel() {

    }

    public int getCardNr() {
        return cardNr;
    }

    public void setCardNr(int cardNr) {
        this.cardNr = cardNr;
    }

    public void incrementCardNr() {
        ++cardNr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CardModel> getHand() {
        return hand;
    }

    public void setHand(ArrayList<CardModel> hand) {
        this.hand = hand;
    }


}

