package com.pulley.captrivia.model.questions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * A class to deserialize questions from JSON.
 */
public class QuestionsLoader {

    private ObjectMapper objectMapper;

    public QuestionsLoader() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Load the list of questions from the given file, and shuffle them before returning.
     */
    public List<Question> loadFromFile(String filepath) throws IOException {
        List<Question> questions = objectMapper.readValue(new File(filepath), new TypeReference<List<Question>>(){});
        Collections.shuffle(questions);
        return questions;
    }
}
