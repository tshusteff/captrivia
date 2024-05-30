package com.pulley.captrivia.model.gameevent;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class GameEventPlayerEnter extends GameEventType {
    String name;
    String[] players;
    Map<String, Boolean> players_ready;
    int question_count;

    public GameEventPlayerEnter() {
    }

    public GameEventPlayerEnter(String name, String[] players, Map<String, Boolean> players_ready, int question_count) {
        this.name = name;
        this.players = players;
        this.players_ready = players_ready;
        this.question_count = question_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public Map<String, Boolean> getPlayers_ready() {
        return players_ready;
    }

    public void setPlayers_ready(Map<String, Boolean> players_ready) {
        this.players_ready = players_ready;
    }

    public int getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(int question_count) {
        this.question_count = question_count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventPlayerEnter)) return false;
        GameEventPlayerEnter that = (GameEventPlayerEnter) o;
        return getQuestion_count() == that.getQuestion_count() && getName().equals(that.getName()) && Arrays.equals(getPlayers(), that.getPlayers()) && getPlayers_ready().equals(that.getPlayers_ready());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getName(), getPlayers_ready(), getQuestion_count());
        result = 31 * result + Arrays.hashCode(getPlayers());
        return result;
    }

    @Override
    public String toString() {
        return "GameEventPlayerEnter{" +
                "name='" + name + '\'' +
                ", players=" + Arrays.toString(players) +
                ", players_ready=" + players_ready +
                ", question_count=" + question_count +
                '}';
    }
}
