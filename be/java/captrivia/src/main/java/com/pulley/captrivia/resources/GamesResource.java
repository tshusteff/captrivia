package com.pulley.captrivia.resources;

import com.google.common.collect.ImmutableList;
import com.pulley.captrivia.model.game.Game;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
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

    // returns playercount
    public static int addPlayerToGame(UUID gameId, String player) {
        for (Game game : games) {
            log.info("Existing game with id " + game.getId());
        }
        Game theGame = games.stream()
                .filter( game -> game.getId().equals(gameId))
                .findAny()
                .orElse(null);
        if (theGame != null) {
            log.error("Adding player "+player+" to game with gameId "+gameId);
            theGame.addPlayer(player);
            return theGame.getPlayerCount();
        } else {
            log.error("Did not find game with gameId "+gameId);
            return 0;
        }
    }

}

