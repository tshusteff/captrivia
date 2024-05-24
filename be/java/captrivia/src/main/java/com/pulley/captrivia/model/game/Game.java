package com.pulley.captrivia.model.game;

public class Game {

        private String id;
        private String name;
        private int numQuestions;
        // TODO will want a state variable I think

        public Game(String id, String name, int numQuestions) {
            this.id = id;
            this.name = name;
            this.numQuestions = numQuestions;

        }

        public String getId() {
        return id;
    }
        public String getName() {
            return name;
        }

        public int getNumQuestions() {
            return numQuestions;
        }

    }
