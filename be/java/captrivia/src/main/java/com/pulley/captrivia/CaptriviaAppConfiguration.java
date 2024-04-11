package com.pulley.captrivia;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;

public class CaptriviaAppConfiguration extends Configuration {
    private String questionsFile;

    @JsonProperty
    public String getQuestionsFile() {
        return questionsFile;
    }

    @JsonProperty
    public void setQuestionsFile(String questionsFile) {
        this.questionsFile = questionsFile;
    }
}
