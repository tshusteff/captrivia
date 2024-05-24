package com.pulley.captrivia.resources;

import com.google.common.collect.ImmutableList;
import com.pulley.captrivia.model.game.Game;
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
            new Game("1", "Game1", 3 ),
            new Game("2", "Game2",  2 )
        );

        return games;
    }

}

