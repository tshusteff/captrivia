package com.pulley.captrivia.model.gameevent;

import java.util.Objects;

public class GameEventCountdown extends GameEventType {
    int seconds;

    public GameEventCountdown() {
    }

    public GameEventCountdown(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventCountdown that)) return false;
        return getSeconds() == that.getSeconds();
    }

    public int hashCode() {
        return Objects.hash(getSeconds());
    }

    public String toString() {
        return "GameEventCountdown{" +
                "seconds=" + seconds +
                '}';
    }
}
