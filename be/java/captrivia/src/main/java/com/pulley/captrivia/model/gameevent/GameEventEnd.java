package com.pulley.captrivia.model.gameevent;

import java.util.Arrays;
import java.util.Objects;

public class GameEventEnd extends GameEventType {
    PlayerScore[] scores;

    public GameEventEnd() {
    }

    public GameEventEnd(PlayerScore[] scores) {
        this.scores = scores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventEnd)) return false;
        GameEventEnd that = (GameEventEnd) o;
        return Arrays.equals(scores, that.scores);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(scores);
    }

    @Override
    public String toString() {
        return "GameEventEnd{" +
                "scores=" + Arrays.toString(scores) +
                '}';
    }
}
