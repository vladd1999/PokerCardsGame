package com.backend.model;

public class PlayerSession {
    private int id;

    private int playerId;

    public PlayerSession() {

    }
    public PlayerSession(int id, int playerId) {
        this.id = id;
        this.playerId = playerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    @Override
    public String toString() {
        return "PlayerSession{" +
                "id=" + id +
                ", playerId=" + playerId +
                '}';
    }
}
