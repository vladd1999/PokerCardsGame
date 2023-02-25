package com.backend.utils;
import com.backend.model.GameModel;
import com.backend.model.PlayerModel;

import java.util.List;


public interface Dealing {

    void deal(List<PlayerModel> playerModels);

    public void setCards(GameModel gameModel);

}
