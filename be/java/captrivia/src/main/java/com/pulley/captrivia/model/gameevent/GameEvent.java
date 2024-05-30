package com.pulley.captrivia.model.gameevent;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;
import java.util.UUID;

public class GameEvent {
    UUID id;
    @JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type", include=JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = GameEventCreate.class, name = "game_create"),
            @JsonSubTypes.Type(value = GameEventStateChange.class, name = "game_state_change"),
            @JsonSubTypes.Type(value = GameEventPlayerCount.class, name = "game_player_count"),
            @JsonSubTypes.Type(value = GameEventDestroy.class, name = "game_destroy"),
            @JsonSubTypes.Type(value = GameEventStart.class, name = "game_start"),
            @JsonSubTypes.Type(value = GameEventEnd.class, name = "game_end"),
            @JsonSubTypes.Type(value = GameEventCountdown.class, name = "game_countdown"),
            @JsonSubTypes.Type(value = GameEventQuestion.class, name = "game_question"),
            @JsonSubTypes.Type(value = GameEventPlayerEnter.class, name = "game_player_enter"),
            @JsonSubTypes.Type(value = GameEventPlayerJoin.class, name = "game_player_join"),
            @JsonSubTypes.Type(value = GameEventPlayerReady.class, name = "game_player_ready"),
            @JsonSubTypes.Type(value = GameEventPlayerLeave.class, name = "game_player_leave"),
            @JsonSubTypes.Type(value = GameEventPlayerCorrect.class, name = "game_player_correct"),
            @JsonSubTypes.Type(value = GameEventPlayerIncorrect.class, name = "game_player_incorrect")
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