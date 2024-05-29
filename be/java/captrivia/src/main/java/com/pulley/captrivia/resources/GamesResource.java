package com.pulley.captrivia.resources;

import com.google.common.collect.ImmutableList;
import com.pulley.captrivia.model.game.Game;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
public class GamesResource {
    static private List<Game> games = new ArrayList<Game>();

    // TODO: Fix this so that the data is not hardcoded and is in the right
    // shape that the frontend expects
    @GET
    public List<Game> getGames() {
        return games;
    }

    public static void addGame(Game game) {
        games.add(game);
    }

}

