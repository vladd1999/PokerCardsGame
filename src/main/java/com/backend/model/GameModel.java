package com.backend.model;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    public DealerCardModel dealerCardModel = new DealerCardModel();
    private int id;
    private int maxPlayer = 2;
    private ArrayList<PlayerModel> playerModels;
    private ArrayList<CardModel> tableCardModels;
    private InformationModel lastInformationModel = null;
    private Integer currentPlayerId;
    private TypeOfMove typeOfMove;
    private Long endAt = null;

    public GameModel() {
        playerModels = new ArrayList<>();
    }

    public GameModel(int id) {
        this.id = id;
        playerModels = new ArrayList<>();
    }

    public GameModel(int id, ArrayList<CardModel> cardModels, ArrayList<PlayerModel> playerModels) {
        this.id = id;
        this.tableCardModels = cardModels;
        this.playerModels = playerModels;

    }

    public Long getEndAt() {
        return endAt;
    }

    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    public TypeOfMove getTypeOfMove() {
        return typeOfMove;
    }

    public void setTypeOfMove(TypeOfMove typeOfMove) {
        this.typeOfMove = typeOfMove;
    }

    public Integer getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(Integer currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public InformationModel getLastInformation() {
        return lastInformationModel;
    }

    public void setLastInformation(InformationModel lastInformationModel) {
        this.lastInformationModel = lastInformationModel;
    }


    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", maxPlayer=" + maxPlayer +
                ", cards=" + tableCardModels +
                ", players=" + playerModels +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getMaxPlayer() {
        return maxPlayer;
    }

    public List<CardModel> getTableCards() {
        return tableCardModels;
    }

    public void setTableCards(ArrayList<CardModel> tableCardModels) {
        this.tableCardModels = tableCardModels;
    }

    public List<PlayerModel> getPlayerModels() {
        return playerModels;
    }

    public void setPlayers(PlayerModel playerModel) {
        this.playerModels.add(playerModel);
    }

}
