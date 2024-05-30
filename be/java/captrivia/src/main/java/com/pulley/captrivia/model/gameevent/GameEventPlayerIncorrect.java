package com.pulley.captrivia.model.gameevent;

import java.util.Objects;
import java.util.UUID;

public class GameEventPlayerIncorrect extends GameEventType {
    UUID id;
    String player;

    public GameEventPlayerIncorrect() {
    }

    public GameEventPlayerIncorrect(UUID id, String player) {
        this.id = id;
        this.player = player;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventPlayerIncorrect)) return false;
        GameEventPlayerIncorrect that = (GameEventPlayerIncorrect) o;
        return getId().equals(that.getId()) && getPlayer().equals(that.getPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlayer());
    }

    @Override
    public String toString() {
        return "GameEventPlayerIncorrect{" +
                "id=" + id +
                ", player='" + player + '\'' +
                '}';
    }
}
