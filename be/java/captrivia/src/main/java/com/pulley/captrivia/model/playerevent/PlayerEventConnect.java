package com.pulley.captrivia.model.playerevent;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pulley.captrivia.model.playercommand.PlayerCommandCreate;

import java.util.Objects;

// need this because this is an empty bean
@JsonSerialize
public class PlayerEventConnect extends PlayerEventType {
    public PlayerEventConnect() {
    }

    @Override
    public String toString() {
        return "PlayerEventConnect{}";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerEventConnect that)) return false;
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.getClass());
    }

}
