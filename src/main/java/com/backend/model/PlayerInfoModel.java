package com.backend.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoModel {


    private List<Card> hand;
    private int id;
    private int numberOfCards = 1;
    private String name;

    public PlayerInfoModel(int id, String name, int numberOfCards) {
        this.id = id;
        this.name = name;
        this.numberOfCards = numberOfCards;
    }

    public PlayerInfoModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PlayerInfoModel() {

    }
    public PlayerInfoModel(PlayerInfoModel playerInfoModel) {
        this.id=playerInfoModel.getId();
        this.name= new String(playerInfoModel.getName());
        this.numberOfCards=playerInfoModel.getNumberOfCards();
        this.hand=Card.copyOf(playerInfoModel.getHand());
    }
    public static List<PlayerInfoModel> copyOf(List<PlayerInfoModel> players){
        List<PlayerInfoModel> playerList = new ArrayList<>();
        for(PlayerInfoModel player:players){
            playerList.add(new PlayerInfoModel(player));
        }
        return  playerList;
    }
    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public void incrementCardNr() {
        ++numberOfCards;
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

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

}
