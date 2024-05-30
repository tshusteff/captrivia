package com.pulley.captrivia.model.gameevent;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class GameEventQuestion extends GameEventType {
    UUID id;
    String[] options;
    String question;

    public GameEventQuestion() {
    }

    public GameEventQuestion(UUID id, String[] options, String question) {
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

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
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
        return getId().equals(that.getId()) && Arrays.equals(getOptions(), that.getOptions()) && getQuestion().equals(that.getQuestion());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getQuestion());
        result = 31 * result + Arrays.hashCode(getOptions());
        return result;
    }

    @Override
    public String toString() {
        return "GameEventQuestion{" +
                "id=" + id +
                ", options=" + Arrays.toString(options) +
                ", question='" + question + '\'' +
                '}';
    }
}
