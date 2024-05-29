package com.pulley.captrivia.model.playercommand;

import java.util.Objects;
import java.util.UUID;

public class PlayerCommandJoin extends PlayerCommandType {
    UUID game_id;

    public PlayerCommandJoin() {
    }

    public PlayerCommandJoin(UUID game_id) {
        this.game_id = game_id;
    }
    public UUID getGame_id() {
        return game_id;
    }

    public void setGame_id(UUID game_id) {
        this.game_id = game_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerCommandJoin)) return false;
        PlayerCommandJoin that = (PlayerCommandJoin) o;
        return Objects.equals(getGame_id(), that.getGame_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGame_id());
    }



}
