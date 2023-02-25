package com.backend.model;
import com.backend.utils.Dealing;

import java.util.ArrayList;
import java.util.List;

public class DealerCardModel implements Dealing {

    DeckModel deckModel;

    public DealerCardModel() {
        this.deckModel = new DeckModel();
    }


    public CardModel getCard(int index) {
        return deckModel.get(index);
    }

    public CardModel drawCard(int index) {

        CardModel cardModel = getCard(index);
        deckModel.remove(index);
        return cardModel;
    }


    public void deal(List<PlayerModel> playerModels) {
        playerModels.forEach(player->{
            ArrayList<CardModel> playerCards=new ArrayList<>();
            for (int i = 0; i < player.getCardNr(); i++) {
                CardModel cardModel = drawCard(0);
                playerCards.add(cardModel);
            }
        player.setHand(playerCards);
        });
    }

    public void setCards(GameModel gameModel) {
        ArrayList<CardModel> table = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CardModel cardModel = drawCard(0);
            table.add(cardModel);
        }
        gameModel.setTableCards(table);
        deal(gameModel.getPlayerModels());
    }

}
