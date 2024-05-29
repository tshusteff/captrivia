package com.pulley.captrivia.model.gameevent;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;
import java.util.UUID;

public class GameEvent {
    UUID id;
    @JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type", include=JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = GameEventCreate.class, name = "game_create")
    })
    GameEventType payload;


    public GameEvent() {
    }

    public GameEvent(UUID id, GameEventType payload) {
        this.id = id;
        this.payload = payload;
    }

    public UUID getId() {
        return id;
    }


    public GameEventType getPayload() {
        return payload;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEvent)) return false;
        GameEvent that = (GameEvent) o;
        return getId().equals(that.getId()) && getPayload().equals(that.getPayload());
    }

    public int hashCode() {
        return Objects.hash(getId(), getPayload());
    }

    public String toString() {
        return "GameEvent{" +
                "id='" + id + '\'' +
                ", payload=" + payload +
                '}';
    }
}