package com.pulley.captrivia.model.gameevent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GameEventQuestion extends GameEventType {
    UUID id;
    List<String> options;
    String question;

    public GameEventQuestion(UUID id, List<String> options, String question) {
        this.id = id;
        this.options = options;
        this.question = question;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEventQuestion)) return false;
        GameEventQuestion that = (GameEventQuestion) o;
        return getId().equals(that.getId()) && getOptions().equals(that.getOptions()) && getQuestion().equals(that.getQuestion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOptions(), getQuestion());
    }

    @Override
    public String toString() {
        return "GameEventQuestion{" +
                "id=" + id +
                ", options=" + options +
                ", question='" + question + '\'' +
                '}';
    }
}
