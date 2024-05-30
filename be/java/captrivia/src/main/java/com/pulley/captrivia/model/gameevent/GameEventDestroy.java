package com.pulley.captrivia.model.gameevent;

import java.util.Objects;

public class GameEventDestroy extends GameEventType {

    public GameEventDestroy() {
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventDestroy that)) return false;
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.getClass());
    }

    public String toString() {
        return "GameEventDestroy{" +
                '}';
    }
}
