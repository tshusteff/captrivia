package com.pulley.captrivia.model.gameevent;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;

@JsonSerialize
public class PlayerScore {
    String name;
    int score;


    public PlayerScore() {}

    public PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerScore)) return false;
        PlayerScore that = (PlayerScore) o;
        return getScore() == that.getScore() && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getScore());
    }

    @Override
    public String toString() {
        return "PlayerScore{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
