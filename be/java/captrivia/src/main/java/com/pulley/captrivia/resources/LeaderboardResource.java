package com.pulley.captrivia.resources;

import com.google.common.collect.ImmutableList;
import com.pulley.captrivia.model.leaderboard.LeaderboardEntry;
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
            new LeaderboardEntry("Alice", 3, 5),
            new LeaderboardEntry("John", 2,6 )
        );

        return leaderboard;
    }

}

