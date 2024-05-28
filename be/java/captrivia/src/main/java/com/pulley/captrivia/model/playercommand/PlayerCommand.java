package com.pulley.captrivia.model.playercommand;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

public class PlayerCommand {
    String nonce;
    @JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type", include=JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = PlayerCommandCreate.class, name = "create")
    })
    PlayerCommandType payload;


    public PlayerCommand() {
    }

    public PlayerCommand(String nonce, PlayerCommandType payload) {
        this.nonce = nonce;
        this.payload = payload;
    }

    public String getNonce() {
        return nonce;
    }


    public PlayerCommandType getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerCommand)) return false;
        PlayerCommand that = (PlayerCommand) o;
        return getNonce().equals(that.getNonce()) && getPayload().equals(that.getPayload());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNonce(), getPayload());
    }

    @Override
    public String toString() {
        return "PlayerCommand{" +
                "nonce='" + nonce + '\'' +
                ", payload=" + payload +
                '}';
    }
}