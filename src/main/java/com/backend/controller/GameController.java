package com.backend.controller;

import com.backend.model.*;
import com.backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {
    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(path = "/{id}")
    public GameModel getGame(@PathVariable int id) {
        return gameService.getGame(id);
    }


    @PostMapping(value = "/set-accepted-move")
    @ResponseBody
    void setLastMove(@RequestBody NextAcceptedMoveParams nextAcceptedMoveParams) {
        gameService.setMove(nextAcceptedMoveParams.gameId, nextAcceptedMoveParams.informationModel);
    }

    @PostMapping(value = "/is-lying")
    @ResponseBody
    void isLying(@RequestBody IsLyingParams params) {
        gameService.endRound(params.id);
    }

    @PostMapping(value = "/join")
    @ResponseBody
    PlayerSession joinGame(@RequestBody PlayerModel playerModel) {
        return gameService.joinGame(playerModel.getName());
    }

    @PostMapping("/retrieve-state")
    @ResponseBody
    public GameStateModel retrieveState(@RequestBody PlayerSession playerSession) {
        return gameService.getGameSession(playerSession);
    }

    public static class IsLyingParams {
        public int id;
    }

    public static class NextAcceptedMoveParams {
        public int gameId;
        public Information informationModel;

    }
}
