package com.backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameStateModel {
    private int id;
    private int maxPlayer = 2;
    private List<PlayerInfoModel> players;
    private Integer currentPlayerId;// if null => game didnt start yet
    private TypeOfMove typeOfMove = TypeOfMove.waiting; //0-not your turn, 1- pick hand, 2- trust or not
    private List<Card> tableCardModels;
    private InformationModel lastInfo;

    public GameStateModel() {

    }

    public GameStateModel(GameStateModel gameStateModel) {
        this.id = gameStateModel.getId();
        this.tableCardModels = Card.copyOf(gameStateModel.getTableCardModels());
        this.players = PlayerInfoModel.copyOf(gameStateModel.getPlayers());
        this.lastInfo = InformationModel.copyOf(gameStateModel.getLastInfo());
        this.typeOfMove = gameStateModel.getTypeOfMove();
        this.maxPlayer = gameStateModel.getMaxPlayer();
        this.currentPlayerId = gameStateModel.getCurrentPlayerId();
    }

    public GameStateModel(GameModel gameModel, int playerId) {
        id = gameModel.getId();
        players = new ArrayList<>();
        Long now = (new Date()).getTime();
        currentPlayerId = gameModel.getCurrentPlayerId();
        if (gameModel.getEndAt() == null) {
            for (PlayerModel playerModel : gameModel.getPlayerModels()) {
                PlayerInfoModel playerInfoModel = new PlayerInfoModel(playerModel.getId(), playerModel.getName(), playerModel.getCardNr());
                players.add(playerInfoModel);
                if (playerModel.getId() == playerId) {
                    playerInfoModel.setHand(toNumericCards(playerModel.getHand()));
                }
            }
        } else {
            if (now - gameModel.getEndAt() > 10000) {
                gameModel.setEndAt(-1L);
            }
            currentPlayerId = null;
            for (PlayerModel playerModel : gameModel.getPlayerModels()) {
                PlayerInfoModel playerInfoModel = new PlayerInfoModel(playerModel.getId(), playerModel.getName(), playerModel.getCardNr());
                players.add(playerInfoModel);
                playerInfoModel.setHand(toNumericCards(playerModel.getHand()));
            }
        }

        tableCardModels = toNumericCards(gameModel.getTableCards());
        if (gameModel.getCurrentPlayerId() != null && gameModel.getCurrentPlayerId() == playerId) {

            typeOfMove = gameModel.getTypeOfMove();
        }
        lastInfo = gameModel.getLastInformation();
    }


    private List<Card> toNumericCards(List<CardModel> hand) {
        if (hand != null) {
            List<Card> cards = new ArrayList<>();
            for (CardModel card :
                    hand) {
                cards.add(new Card(card.getSuit().ordinal(), card.getRank().ordinal()));
            }
            return cards;
        }
        return null;
    }

    public List<Card> getTableCardModels() {
        return tableCardModels;
    }

    public void setTableCardModels(List<Card> tableCardModels) {
        this.tableCardModels = tableCardModels;
    }


    public Integer getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(Integer currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public TypeOfMove getTypeOfMove() {
        return typeOfMove;
    }

    public void setTypeOfMove(TypeOfMove typeOfMove) {
        this.typeOfMove = typeOfMove;
    }

    public InformationModel getLastInfo() {
        return lastInfo;
    }

    public void setLastInfo(InformationModel lastInfo) {
        this.lastInfo = lastInfo;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", maxPlayer=" + maxPlayer +
                ", players=" + players +
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

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public List<PlayerInfoModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerInfoModel> players) {
        this.players = players;
    }

    public void setPlayers(PlayerInfoModel player) {
        this.players.add(player);
    }
}
