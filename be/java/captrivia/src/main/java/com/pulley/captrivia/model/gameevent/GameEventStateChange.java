package com.pulley.captrivia.model.gameevent;

import java.util.Objects;

public class GameEventStateChange extends GameEventType {
    String state;

    public GameEventStateChange() {
    }

    public GameEventStateChange(String state, int question_count) {
        this.state = state;
    }

    public String getState() {
        return state;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventStateChange that)) return false;
        return getState().equals(that.getState());
    }

    public int hashCode() {
        return Objects.hash(getState());
    }

    public String toString() {
        return "GameEventStateChange{" +
                "state='" + state + '\'' +
                '}';
    }
}
