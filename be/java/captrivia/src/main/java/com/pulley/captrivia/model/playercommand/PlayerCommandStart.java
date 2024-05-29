package com.pulley.captrivia.model.playercommand;

import java.util.Objects;
import java.util.UUID;

//INFO  [2024-05-29 03:36:07,796] com.pulley.captrivia.PlayerConnectServerEndpoint: Server: Received Message: {"type":"join","p
//        ayload":{"game_id":"52ca63cc-d0d0-4750-b0cf-56a72ee6fa69"},"nonce":"452db906-107b-4ed6-af94-33859bc15c50"}
//        E
public class PlayerCommandStart extends PlayerCommandType {
    UUID game_id;

    public PlayerCommandStart() {
    }

    public PlayerCommandStart(UUID game_id) {
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
        if (!(o instanceof PlayerCommandStart)) return false;
        PlayerCommandStart that = (PlayerCommandStart) o;
        return Objects.equals(getGame_id(), that.getGame_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGame_id());
    }

}
