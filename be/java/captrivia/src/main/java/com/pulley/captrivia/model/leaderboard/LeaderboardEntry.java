package com.pulley.captrivia.model.leaderboard;

// TODO: This class should probably be moved somewhere else
public class LeaderboardEntry {
    private String name;
    //        private double score;
    private double acc;
    private double avgSec;
    private int numCorrect;
    private int numQuestion;

    public LeaderboardEntry(String name, double acc, double avgSec, int numCorrect, int numQuestion) {
        this.name = name;
//            this.score = score;
        this.acc = acc;
        this.avgSec = avgSec;
        this.numCorrect = numCorrect;
        this.numQuestion = numQuestion;

    }

    public String getName() {
        return name;
    }

    //        public double getScore() {
//            return score;
//        }
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
