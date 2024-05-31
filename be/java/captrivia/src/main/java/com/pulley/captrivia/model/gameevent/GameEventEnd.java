package com.pulley.captrivia.model.gameevent;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameEventEnd extends GameEventType {
    List<PlayerScore> scores;

    public GameEventEnd(){};

    public GameEventEnd(List<PlayerScore> scores) {
        this.scores = scores;
    }

    public List<PlayerScore> getScores() {
        return scores;
    }

    public void setPlayers(List<PlayerScore> scores) {
        this.scores = scores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventEnd)) return false;
        GameEventEnd that = (GameEventEnd) o;
        return getScores().equals(that.getScores());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScores());
    }

    @Override
    public String toString() {
        return "GameEventEnd{" +
                ", scores=" + scores +
                '}';
    }
}
