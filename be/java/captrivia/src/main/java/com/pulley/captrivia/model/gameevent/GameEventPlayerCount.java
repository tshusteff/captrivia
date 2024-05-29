package com.pulley.captrivia.model.gameevent;

import java.util.Objects;

public class GameEventPlayerCount extends GameEventType {
    int player_count;

    public GameEventPlayerCount() {
    }

    public GameEventPlayerCount(int player_count) {
        this.player_count = player_count;
    }

    public int getQuestion_count() {
        return player_count;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventPlayerCount that)) return false;
        return getQuestion_count() == that.getQuestion_count();
    }

    public int hashCode() {
        return Objects.hash(getQuestion_count());
    }

    public String toString() {
        return "GameEventCreate{" +
                "player_count=" + player_count +
                '}';
    }
}
