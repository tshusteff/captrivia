package com.pulley.captrivia.model.playerevent;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;

// need this because this is an empty bean
@JsonSerialize
public class PlayerEventDisconnect extends PlayerEventType {
    public PlayerEventDisconnect() {
    }

    @Override
    public String toString() {
        return "PlayerEventDisconnect{}";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerEventDisconnect that)) return false;
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.getClass());
    }

}
