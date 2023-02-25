package com.backend.service;

import com.backend.model.*;
import com.backend.repository.GameRepository;
import com.backend.utils.PokerHandPower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GameService {


    @Autowired
    private GameRepository gameRepository;

    public static PokerHandPower getPokerHandPowerByOrdinal(int id) {
        for (PokerHandPower pokerHandPower : PokerHandPower.values()) {
            if (pokerHandPower.getOrdinal() == id) return pokerHandPower;
        }
        return null;
    }

    public void setMove(int gameId, Information newInformation) {
        GameModel gameModel = gameRepository.getGame(gameId);
        if (gameModel != null) {
            InformationModel actualInformation = gameModel.getLastInformation();
            Integer index = getIndexById(gameModel, gameModel.getCurrentPlayerId());
            InformationModel newInfo = createGameInformation(newInformation);
            if (newInfo != null && index != null && ((newInfo.greaterThan(actualInformation)))) {
                gameRepository.getGame(gameId).setLastInformation(newInfo);
                gameModel.setCurrentPlayerId(gameModel.getPlayerModels().get((index + 1) % gameModel.getMaxPlayer()).getId());
                gameModel.setTypeOfMove(TypeOfMove.trustOrNot);
            }
        }
    }

    private InformationModel createGameInformation(Information newInformation) {
        if (getPokerHandPowerByOrdinal(newInformation.getPokerHandPower()) != null && newInformation.getValue() > -1 && newInformation.getValue() < 13) {
            PokerHandPower pokerHandPower = PokerHandPower.values()[newInformation.getPokerHandPower()];
            if (pokerHandPower == PokerHandPower.TWOPAIRS || pokerHandPower == PokerHandPower.FULLHOUSE) {
                if (newInformation.getSecondValue() > -1 && newInformation.getSecondValue() < 13) {
                    return new InformationModel(getPokerHandPowerByOrdinal(newInformation.getPokerHandPower()), newInformation.getValue(), newInformation.getSecondValue());
                }
            } else if (pokerHandPower == PokerHandPower.STRAIGHTFLUSH) {
                if (newInformation.getSecondValue() > -1 && newInformation.getSecondValue() < 4) {
                    return new InformationModel(getPokerHandPowerByOrdinal(newInformation.getPokerHandPower()), newInformation.getValue(), newInformation.getSecondValue());
                }
            } else if (newInformation.getSecondValue() == null) {
                return new InformationModel(getPokerHandPowerByOrdinal(newInformation.getPokerHandPower()), newInformation.getValue(), newInformation.getSecondValue());
            }
        }
        return null;
    }

    private Integer getIndexById(GameModel gameModel, int playerId) {
        for (int i = 0; i < gameModel.getMaxPlayer(); i++) {
            if (gameModel.getPlayerModels().get(i).getId() == playerId) {
                return i;
            }
        }
        return null;
    }

    public boolean isLying(GameModel gameModel) {

        List<PlayerModel> playerModels = gameModel.getPlayerModels();
        InformationModel lastGameInfo = gameModel.getLastInformation();
        int[] allCards = new int[13];
        playerModels.forEach(player ->
                player.getHand().forEach(card -> allCards[card.getRank().ordinal()]++));
        gameModel.getTableCards().forEach(card -> allCards[card.getRank().ordinal()]++);

        PokerHandPower pokerHandPower = lastGameInfo.getPokerHandPower();
        if (pokerHandPower.name().equals(PokerHandPower.HIGHCARD.name())) {
            if (allCards[lastGameInfo.getValue()] == 0) {
                return true;
            } else return false;
        } else if (pokerHandPower.name().equals(PokerHandPower.PAIR.name())) {
            if (allCards[lastGameInfo.getValue()] < 2) {
                return true;
            } else return false;
        } else if (pokerHandPower.name().equals(PokerHandPower.TWOPAIRS.name())) {
            if (allCards[lastGameInfo.getValue()] < 2 || allCards[lastGameInfo.getSecondValue()] < 2) {
                return true;
            } else return false;
        } else if (pokerHandPower.name().equals(pokerHandPower.THREEOFAKIND.name())) {
            if (allCards[lastGameInfo.getValue()] < 3) {
                return true;
            } else return false;
        } else if (pokerHandPower.name().equals(PokerHandPower.STRAIGHT.name())) {
            if (lastGameInfo.getValue() <= 8) {
                for (int i = 0; i < 5; i++) {
                    if (allCards[lastGameInfo.getValue() + i] == 0) {
                        return true;
                    }
                }
                return false;
            } else if (lastGameInfo.getValue() == 12) {
                if (allCards[12] == 0 ||
                        (allCards[0] == 0) ||
                        (allCards[1] == 0) ||
                        (allCards[2] == 0) ||
                        (allCards[3] == 0)) {
                    return true;
                } else return false;
            }
        } else if (pokerHandPower.name().equals(PokerHandPower.FULLHOUSE.name())) {
            if (allCards[lastGameInfo.getValue()] < 3 || allCards[lastGameInfo.getSecondValue()] < 2) {
                return true;
            } else return false;
        } else if (pokerHandPower.name().equals(PokerHandPower.FOUROFAKIND.name())) {

            if (allCards[lastGameInfo.getValue()] < 4) {
                return true;
            } else return false;

        } else {
            int[] cardColor = new int[52];
            if (pokerHandPower.name().equals(PokerHandPower.STRAIGHTFLUSH.name())) {

                //ma duc prin player:players si iau culoarea si valoarea din players hand, si dupa din tablecards
                //pot sa incerc la culoare cu vector[rank] care are 52 carti si aleg culoarea dupa rank=pozitie in vector - culoare x 13
                playerModels.forEach(player ->
                        player.getHand().forEach(card -> cardColor[card.getRank().ordinal() + 13 * card.getSuit().ordinal()] = card.getSuit().ordinal()));

                gameModel.getTableCards().forEach(card -> cardColor[card.getRank().ordinal() + 13 * card.getSuit().ordinal()] = card.getSuit().ordinal());

                if (lastGameInfo.getValue() <= 10)
                    for (int i = 0; i < 5; i++) {
                        if (allCards[lastGameInfo.getValue() + i] == 0 || cardColor[lastGameInfo.getSecondValue() * 13 + lastGameInfo.getValue()] != lastGameInfo.getSecondValue())
                            return true;
                    }
                else if (allCards[12] == 0 || cardColor[lastGameInfo.getSecondValue() * 13 + 12] != lastGameInfo.getSecondValue() ||
                        (allCards[0] == 0 || cardColor[lastGameInfo.getSecondValue() * 13] != lastGameInfo.getSecondValue()) ||
                        (allCards[1] == 0 || cardColor[lastGameInfo.getSecondValue() * 13 + 1] != lastGameInfo.getSecondValue()) ||
                        (allCards[2] == 0 || cardColor[lastGameInfo.getSecondValue() * 13 + 2] != lastGameInfo.getSecondValue()) ||
                        (allCards[3] == 0 || cardColor[lastGameInfo.getSecondValue() * 13 + 3] != lastGameInfo.getSecondValue()))
                    return true;
            }
        }

        return true;
    }

    public void endRound(int gameId) {
        GameModel gameModel = getGame(gameId);
        gameModel.setEndAt((new Date()).getTime());
    }

    public void newRound(GameModel gameModel) {

        Integer index = getIndexById(gameModel, gameModel.getCurrentPlayerId());
        if (index != null) {
            if (!isLying(gameModel)) {
                gameModel.getPlayerModels().get(index).incrementCardNr();
            } else {
                gameModel.getPlayerModels().get((index + gameModel.getMaxPlayer() - 1) % gameModel.getMaxPlayer()).incrementCardNr();
            }
            gameModel.dealerCardModel.setCards(gameModel);
            gameModel.setLastInformation(null);
            gameModel.setTypeOfMove(TypeOfMove.pickHand);
        }
    }

    public GameModel getGame(int id) {
        return gameRepository.getGame(id)
                ;
    }

    public PlayerSession joinGame(String name) {
        return gameRepository.joinGame(name);
    }

    public GameStateModel getGameSession(PlayerSession playerSession) {
        return gameRepository.getGameSession(playerSession);
    }

    @Scheduled(fixedDelay = 5000)
    public void createNewRound() {
        for (GameModel gameModel : gameRepository.getGames()) {
            if (gameModel.getEndAt() != null && gameModel.getEndAt() == -1L) {
                gameModel.setEndAt(null);
                newRound(gameModel);
            }
        }
    }

}
