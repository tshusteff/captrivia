package com.pulley.captrivia.model.playercommand;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Objects;

public class PlayerCommandCreate extends PlayerCommandType {
    String name;
    int question_count;

    public PlayerCommandCreate() {
    }

    public PlayerCommandCreate(String name, int question_count) {
        this.name = name;
        this.question_count = question_count;
    }

    public String getName() {
        return name;
    }

    public int getQuestion_count() {
        return question_count;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerCommandCreate that)) return false;
        return getQuestion_count() == that.getQuestion_count() && getName().equals(that.getName());
    }

    public int hashCode() {
        return Objects.hash(getName(), getQuestion_count());
    }

    public String toString() {
        return "PlayerCommandCreate{" +
                "name='" + name + '\'' +
                ", question_count=" + question_count +
                '}';
    }
}
