package com.pulley.captrivia.model.playerevent;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pulley.captrivia.model.playercommand.PlayerCommandCreate;

import java.util.Objects;


@JsonSerialize // need this because this is an empty bean
public class PlayerEventConnect extends PlayerEventType {
    public PlayerEventConnect() {
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerEventConnect that)) return false;
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.getClass());
    }

    @Override
    public String toString() {
        return "PlayerEventConnect{}";
    }

}
