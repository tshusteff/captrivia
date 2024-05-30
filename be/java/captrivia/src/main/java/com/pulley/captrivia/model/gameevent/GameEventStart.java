package com.pulley.captrivia.model.gameevent;

import java.util.Objects;

public class GameEventStart extends GameEventType {

    public GameEventStart() {
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventStart that)) return false;
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.getClass());
    }

    public String toString() {
        return "GameEventStart{" +
                '}';
    }
}
