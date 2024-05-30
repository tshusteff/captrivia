package com.pulley.captrivia.model.gameevent;

import java.util.Objects;

public class GameEventPlayerCount extends GameEventType {
    int player_count;

    public GameEventPlayerCount() {
    }

    public GameEventPlayerCount(int player_count) {
        this.player_count = player_count;
    }

    public int getPlayer_count() {
        return player_count;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventPlayerCount that)) return false;
        return getPlayer_count() == that.getPlayer_count();
    }

    public int hashCode() {
        return Objects.hash(getPlayer_count());
    }

    public String toString() {
        return "GameEventPlayerCount{" +
                "player_count=" + player_count +
                '}';
    }
}
