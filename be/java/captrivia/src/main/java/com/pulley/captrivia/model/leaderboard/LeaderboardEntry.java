package com.pulley.captrivia.model.leaderboard;

public class LeaderboardEntry {
    private String name;
    //        private double score;
    private int numCorrect;
    private int numQuestion;

    public LeaderboardEntry(String name, int numCorrect, int numQuestion) {
        this.name = name;
        this.numCorrect = numCorrect;
        this.numQuestion = numQuestion;

    }

    public String getName() {
        return name;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public int getNumQuestion() {
        return numQuestion;
    }

    public int getacc() {
        return numCorrect / numQuestion;
    }
}
