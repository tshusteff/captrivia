package com.pulley.captrivia.model.gameevent;

import com.pulley.captrivia.model.playercommand.PlayerCommandType;

import java.util.Objects;

public class GameEventCreate extends GameEventType {
    String name;
    int question_count;

    public GameEventCreate(String name, int question_count) {
        this.name = name;
        this.question_count = question_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(int question_count) {
        this.question_count = question_count;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventCreate that)) return false;
        return getQuestion_count() == that.getQuestion_count() && getName().equals(that.getName());
    }

    public int hashCode() {
        return Objects.hash(getName(), getQuestion_count());
    }

    public String toString() {
        return "GameEventCreate{" +
                "name='" + name + '\'' +
                ", question_count=" + question_count +
                '}';
    }
}
