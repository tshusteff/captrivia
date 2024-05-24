package com.pulley.captrivia.resources;

import com.google.common.collect.ImmutableList;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/leaderboard")
@Produces(MediaType.APPLICATION_JSON)
public class LeaderboardResource {

    // TODO: Fix this so that the data is not hardcoded and is in the right
    // shape that the frontend expects, or maybe not worry about leaderboards at all!
    @GET
    public List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> leaderboard = ImmutableList.of(
            new LeaderboardEntry("Alice", 89.79, .5, 1, 3 ),
            new LeaderboardEntry("John", 65.35,.5, 1, 2 ),
            new LeaderboardEntry("James", 15.92, .5, 1, 5),
            new LeaderboardEntry("Janice", 3.14, .5, 8, 7)
        );

        return leaderboard;
    }

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
        public double getAcc() { return acc; }
        public double getAvgSec() { return avgSec; }
        public int getNumCorrect() { return numCorrect; }
        public int getNumQuestion() { return numQuestion; }
    }
}

