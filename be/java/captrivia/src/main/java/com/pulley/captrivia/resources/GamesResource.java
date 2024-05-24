package com.pulley.captrivia.resources;

import com.google.common.collect.ImmutableList;
import com.pulley.captrivia.model.game.Game;
import com.pulley.captrivia.model.leaderboard.LeaderboardEntry;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
public class GamesResource {

    // TODO: Fix this so that the data is not hardcoded and is in the right
    // shape that the frontend expects, or maybe not worry about leaderboards at all!
    @GET
    public List<Game> getGames() {
        List<Game> games = ImmutableList.of(
            new Game("Game1", 89.79, .5, 1, 3 ),
            new Game("Game2", 65.35,.5, 1, 2 ),
            new Game("James", 15.92, .5, 1, 5),
            new Game("Janice", 3.14, .5, 8, 7)
        );

        return games;
    }

}

