package com.pulley.captrivia.model.playercommand;

import java.util.Objects;
import java.util.UUID;

//INFO  [2024-05-29 03:36:07,796] com.pulley.captrivia.PlayerConnectServerEndpoint: Server: Received Message: {"type":"join","p
//        ayload":{"game_id":"52ca63cc-d0d0-4750-b0cf-56a72ee6fa69"},"nonce":"452db906-107b-4ed6-af94-33859bc15c50"}
//        E
public class PlayerCommandAnswer extends PlayerCommandType {
    UUID game_id;
    int index;
    UUID question_id;

    public PlayerCommandAnswer() {
    }

    public PlayerCommandAnswer(UUID game_id, int index, UUID question_id) {

        this.game_id = game_id;
        this.index = index;
        this.question_id = question_id;
    }
    public UUID getGame_id() {
        return game_id;
    }
    public int getIndex() {return index;}
    public UUID getQuestion_id() {
        return question_id;
    }

    public void setGame_id(UUID game_id) {
        this.game_id = game_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerCommandAnswer)) return false;
        PlayerCommandAnswer that = (PlayerCommandAnswer) o;
        return getIndex() == that.getIndex() && getGame_id().equals(that.getGame_id()) && getQuestion_id().equals(that.getQuestion_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGame_id(), getIndex(), getQuestion_id());
    }
}
