package com.pulley.captrivia.model.playerevent;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

public class PlayerEvent {
    String player;
    @JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type", include=JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = PlayerEventConnect.class, name = "player_connect"),
            @JsonSubTypes.Type(value = PlayerEventDisconnect.class, name = "player_disconnect")
    })
    PlayerEventType payload;


    public PlayerEvent() {
    }

    public PlayerEvent(String player, PlayerEventType payload) {
        this.player = player;
        this.payload = payload;
    }

    public String getPlayer() {
        return player;
    }


    public PlayerEventType getPayload() {
        return payload;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerEvent)) return false;
        PlayerEvent that = (PlayerEvent) o;
        return getPlayer().equals(that.getPlayer()) && getPayload().equals(that.getPayload());
    }

    public int hashCode() {
        return Objects.hash(getPlayer(), getPayload());
    }

    public String toString() {
        return "PlayerEvent{" +
                "player='" + player + '\'' +
                ", payload=" + payload +
                '}';
    }
}