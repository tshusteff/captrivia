package com.pulley.captrivia.model.game;

public class Game {

        private String name;
        private double acc;
        private double avgSec;
        private int numCorrect;
        private int numQuestion;

        public Game(String name, double acc, double avgSec, int numCorrect, int numQuestion) {
            this.name = name;
            this.acc = acc;
            this.avgSec = avgSec;
            this.numCorrect = numCorrect;
            this.numQuestion = numQuestion;

        }

        public String getName() {
            return name;
        }

        public double getAcc() {
            return acc;
        }

        public double getAvgSec() {
            return avgSec;
        }

        public int getNumCorrect() {
            return numCorrect;
        }

        public int getNumQuestion() {
            return numQuestion;
        }

    }
