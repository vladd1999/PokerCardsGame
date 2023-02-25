package com.backend.repository;

import com.backend.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameRepository {
    private static ConcurrentHashMap<Integer, GameModel> gameMap = new ConcurrentHashMap<>();
    private static int id = 0;
    private static int playerId = 0;

    public GameModel getGame(int id) {
        return gameMap.get(id);
    }

    public GameModel createGame() {
        GameModel newGameModel = new GameModel(++id);
        gameMap.put(id, newGameModel);
        return newGameModel;
    }

    public PlayerSession joinGame(String name) {
        GameModel gameModel = gameMap.get(id);
        if (gameModel != null && gameModel.getMaxPlayer() > gameModel.getPlayerModels().size()) {
            gameModel.setPlayers(new PlayerModel(++playerId, name));
            if (gameModel.getMaxPlayer() == gameModel.getPlayerModels().size()) {
                gameModel.setCurrentPlayerId(gameModel.getPlayerModels().get(0).getId());
                gameModel.setTypeOfMove(TypeOfMove.pickHand);
                gameModel.dealerCardModel.setCards(gameModel);

            }
        } else {
            createGame().setPlayers(new PlayerModel(++playerId, name));
        }
        PlayerSession playerSession = new PlayerSession(id, playerId);
        return playerSession;

    }

    public GameStateModel getGameSession(PlayerSession playerSession) {
        return new GameStateModel(gameMap.get(playerSession.getId()), playerSession.getPlayerId());
    }


    public List<GameModel> getGames() {
        return gameMap.values().stream().toList() ;
    }
}
