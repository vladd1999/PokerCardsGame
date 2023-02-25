package com.backend.model;

import com.backend.utils.Suit;

import java.util.ArrayList;


public class PokerHandEvaluator {

    public int[] setUpRankCounter() {

        return new int[13];
    }

    public int[] setUpRoyalRankCounter() {
        int[] royalRankCounter = new int[13];
        royalRankCounter[8] = 0;
        royalRankCounter[9] = 0;
        royalRankCounter[10] = 0;
        royalRankCounter[11] = 0;
        royalRankCounter[12] = 0;
        return royalRankCounter;
    }

    public int[] setUpSuitCounter() {
        return new int[4];
    }

    public boolean isDuplicateRank(CardModel cardModel, ArrayList<CardModel> hand) {
        boolean isDuplicate = false;
        for (CardModel handCardModel : hand) {
            if (cardModel.getRank().ordinal()== handCardModel.getRank().ordinal()) {
                isDuplicate = true;
            }
            return isDuplicate;
        }
        return false;
    }

    //hand evaluator methods

    public CardModel highCard(ArrayList<CardModel> hand) {
        int highestValue = 0;
        CardModel highCardModel = null;
        for (CardModel cardModel : hand) {
            int cardValue = cardModel.getRank().ordinal();
            if (cardValue >= highestValue) {
                highestValue = cardValue;
                highCardModel = cardModel;
            }
        }
        return highCardModel;
    }

    public ArrayList<ArrayList<CardModel>> howManyOfKind(int howMany, ArrayList<CardModel> hand) {
        ArrayList<ArrayList<CardModel>> kindCounter = new ArrayList<>();
        int[] rankCounter = setUpRankCounter();

        //increment rankCounter to determine how many of each kind
        for (CardModel cardModel : hand) {
            int keyOfCard = cardModel.getRank().ordinal();
            int currentValue = rankCounter[keyOfCard];
            rankCounter[keyOfCard] = currentValue + 1;
        }

        //...check if there is the correct number of a kind
        for (int key : rankCounter) {
            int cardCount = rankCounter[key];
            if (cardCount == howMany) {

                //...and if there is, make an ArrayList of those cards
                ArrayList<CardModel> kindArray = new ArrayList<>();
                for (CardModel cardModel : hand) {
                    if ((cardModel.getRank().ordinal()) == key) {
                        kindArray.add(cardModel);
                    }
                }
                //...and add it to the kindCounter ArrayList.
                kindCounter.add(kindArray);
            }
        }
        return kindCounter;
    }

    public ArrayList<CardModel> straight(ArrayList<CardModel> hand) {
        ArrayList<CardModel> straight = new ArrayList<>();
        int[] rankCounter = setUpRankCounter();

        //increment rankCounter to determine how many of each kind
        for (CardModel cardModel : hand) {
            int keyOfCard = cardModel.getRank().ordinal();
            int currentValue = rankCounter[keyOfCard];
            rankCounter[keyOfCard] = (currentValue + 1);
        }

        //if there are cards of five consecutive ranks
        for (int i = 1; i < 10; i++) {
            if ((rankCounter[i] > 0) &&
                    (rankCounter[i + 1] > 0) &&
                    (rankCounter[i + 2] > 0) &&
                    (rankCounter[i + 3] > 0) &&
                    (rankCounter[i + 4] > 0)) {

                //...and if there are, add each one into straight ArrayList
                for (CardModel cardModel : hand) {
                    if (((cardModel.getRank().ordinal() == i) ||
                            (cardModel.getRank().ordinal() == i + 1) ||
                            (cardModel.getRank().ordinal() == i + 2) ||
                            (cardModel.getRank().ordinal() == i + 3) ||
                            (cardModel.getRank().ordinal() == i + 4)) &&
                            !(isDuplicateRank(cardModel, straight))) {
                        straight.add(cardModel);
                    }
                }
            }
        }

        //separate if for low-ace straight
        if ((rankCounter[12] > 0) &&
                (rankCounter[0] > 0) &&
                (rankCounter[1] > 0) &&
                (rankCounter[2] > 0) &&
                (rankCounter[3] > 0)) {
            for (CardModel cardModel : hand) {
                if (((cardModel.getRank().ordinal() == 12) ||
                        (cardModel.getRank().ordinal() == 0) ||
                        (cardModel.getRank().ordinal() == 1) ||
                        (cardModel.getRank().ordinal() == 2) ||
                        (cardModel.getRank().ordinal() == 3)) &&
                        !(isDuplicateRank(cardModel, straight))) {
                    straight.add(cardModel);
                }
            }
        }
        return straight;
    }

    public ArrayList<CardModel> flush(ArrayList<CardModel> hand) {
        ArrayList<CardModel> flush = new ArrayList<>();
        int[] suitCounter = setUpSuitCounter();

        //increment suitCounter to determine how many of each suit
        for (CardModel cardModel : hand) {
            Suit cardSuit = cardModel.getSuit();
            for (Suit suit : Suit.values()) {
                if (cardSuit.equals(suit)) {
                    int currentValue = suitCounter[suit.ordinal()];
                    suitCounter[suit.ordinal()] = currentValue + 1;
                }
            }
        }

        //...check if there is a flush
        for (Suit suit : Suit.values()) {
            if (suitCounter[suit.ordinal()] == 5) {

                //...and if there is, add those cards to the flush ArrayList
                for (CardModel cardModel : hand) {
                    Suit cardSuit = cardModel.getSuit();
                    if (cardSuit.equals(suit)) {
                        flush.add(cardModel);
                    }
                }
            }
        }
        return flush;
    }

    public ArrayList<CardModel> fullHouse(ArrayList<CardModel> hand) {
        ArrayList<CardModel> fullHouse = new ArrayList<>();

        //set up a copy of hand ArrayList, so that elements can be removed w/o removing from hand
        ArrayList<CardModel> workingHand = new ArrayList<>();
        workingHand.addAll(hand);

        //gets ArrayList of three-of-a-kind combinations
        ArrayList<ArrayList<CardModel>> threeOfAKinds = howManyOfKind(3, workingHand);
        for (ArrayList<CardModel> threeOfAKind : threeOfAKinds) {
            if (!(threeOfAKind.isEmpty())) {
                //if there is a three-of-a-kind, remove it from workingHand
                workingHand.removeAll(threeOfAKind);
                //and check workingHand for two-of-a-kinds
                ArrayList<ArrayList<CardModel>> twoOfAKinds = howManyOfKind(2, workingHand);
                //if there is a two-of-a-kind
                if (!(twoOfAKinds.isEmpty())) {
                    //add two-of-a-kind to fullHouse, along with three-of-a-kind, making full house
                    fullHouse.addAll(threeOfAKind);
                    fullHouse.addAll(twoOfAKinds.get(0));
                }
            }
        }
        return fullHouse;
    }

    public ArrayList<CardModel> straightFlush(ArrayList<CardModel> hand) {
        ArrayList<CardModel> straightFlush = new ArrayList<>();

        ArrayList<CardModel> flush = flush(hand);
        //exit early if no flush...
        if (flush.isEmpty()) return straightFlush;
        //else, check if flush is straight
        straightFlush = straight(flush);
        //return straightFlush
        return straightFlush;
    }

    public ArrayList<CardModel> royalFlush(ArrayList<CardModel> hand) {
        ArrayList<CardModel> royalFlush = new ArrayList<>();

        //check if we have a flush
        if (flush(hand).isEmpty()) return royalFlush;

        //...increment royalRankCounter to check how many of each rank
        int[] royalRankCounter = setUpRoyalRankCounter();
        for (CardModel cardModel : hand) {
            int cardRank = cardModel.getRank().ordinal();
            for (int rank : royalRankCounter) {
                if (cardRank==rank) {
                    int currentValue = royalRankCounter[rank];
                    royalRankCounter[rank]= currentValue + 1;
                }
            }
        }

        //...check if there is a royal flush
        if ((royalRankCounter[8] == 1) &&
                (royalRankCounter[9] == 1) &&
                (royalRankCounter[10] == 1) &&
                (royalRankCounter[11] == 1) &&
                (royalRankCounter[12] == 1)) {

            //and if there is, add the cards into royalFlush
            royalFlush.addAll(hand);
        }
        return royalFlush;
    }

    //boolean methods

    public boolean isTwoOfAKind(ArrayList<CardModel> hand) {
        ArrayList<ArrayList<CardModel>> result = howManyOfKind(2, hand);
        return (result.size() == 1);
    }

    public boolean isTwoPair(ArrayList<CardModel> hand) {
        ArrayList<ArrayList<CardModel>> result = howManyOfKind(2, hand);
        return (result.size() == 2);
    }

    public boolean isThreeOfAKind(ArrayList<CardModel> hand) {
        ArrayList<ArrayList<CardModel>> result = howManyOfKind(3, hand);
        return (result.size() == 1);
    }

    public boolean isStraight(ArrayList<CardModel> hand) {
        return !(straight(hand).isEmpty());
    }

    public boolean isFlush(ArrayList<CardModel> hand) {
        return !(flush(hand).isEmpty());
    }

    public boolean isFullHouse(ArrayList<CardModel> hand) {
        return !(fullHouse(hand).isEmpty());
    }

    public boolean isFourOfAKind(ArrayList<CardModel> hand) {
        ArrayList<ArrayList<CardModel>> result = howManyOfKind(4, hand);
        return (result.size() == 1);
    }

    public boolean isStraightFlush(ArrayList<CardModel> hand) {
        return !(straightFlush(hand).isEmpty());
    }

    public boolean isRoyalFlush(ArrayList<CardModel> hand) {
        return !(royalFlush(hand).isEmpty());
    }



//    public int evaluateHand(ArrayList<Card> hand) {
//        if (isRoyalFlush(hand)) {
//            return PokerHandPower.ROYALFLUSH.ordinal();
//        } else if (isStraightFlush(hand)) {
//            return PokerHandPower.STRAIGHTFLUSH.ordinal();
//        } else if (isFourOfAKind(hand)) {
//            return PokerHandPower.FOUROFAKIND.ordinal();
//        } else if (isFullHouse(hand)) {
//            return PokerHandPower.FULLHOUSE.ordinal();
//        } else if (isStraight(hand)) {
//            return PokerHandPower.STRAIGHT.ordinal();
//        } else if (isThreeOfAKind(hand)) {
//            return PokerHandPower.THREEOFAKIND.ordinal();
//        } else if (isTwoPair(hand)) {
//            return PokerHandPower.TWOPAIRS.ordinal();
//        } else if (isTwoOfAKind(hand)) {
//            return PokerHandPower.PAIR.ordinal();
//        } else return 0;
//    }





}
