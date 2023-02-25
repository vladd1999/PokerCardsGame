package com.backend.utils;

public enum PokerHandPower {
    HIGHCARD,
    PAIR,
    TWOPAIRS,
    THREEOFAKIND,
    STRAIGHT,
    FULLHOUSE,
    FOUROFAKIND,
    STRAIGHTFLUSH;

    public int getOrdinal() {
        return this.ordinal();
    }
}
