package com.pulley.captrivia.model.gameevent;

import java.util.Objects;

public class GameEventPlayerLeave extends GameEventType {
    String player;

    public GameEventPlayerLeave() {
    }

    public GameEventPlayerLeave(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventPlayerLeave that)) return false;
        return getPlayer().equals(that.getPlayer());
    }

    public int hashCode() {
        return Objects.hash(getPlayer());
    }

    public String toString() {
        return "GameEventPlayerLeave{" +
                "player='" + player + '\'' +
                '}';
    }
}
