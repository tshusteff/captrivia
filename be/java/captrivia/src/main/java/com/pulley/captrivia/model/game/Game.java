package com.pulley.captrivia.model.game;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {

        private final UUID id;
        private final String name;
        private final int questionCount;

        private int playerCount = 0;

        private String state = "";

        private List<String> players = new ArrayList<String>();


        public Game(UUID id, String name, int questionCount) {
            this.id = id;
            this.name = name;
            this.questionCount = questionCount;
        }

        public UUID getId() {
            return id;
        }
        public String getName() {
            return name;
        }

        public int getQuestionCount() {
            return questionCount;
        }

        public int getPlayerCount() {
            return playerCount;
        }

        public String getState() {
            return state;
        }
    }
