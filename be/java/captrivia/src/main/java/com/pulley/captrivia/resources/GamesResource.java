package com.pulley.captrivia.resources;

import com.google.common.collect.ImmutableList;
import com.pulley.captrivia.model.game.Game;
import com.pulley.captrivia.model.questions.Question;
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
    static private List<Game> games;
    // it seems like we were only given one list of questions; share it.
    static private List<Question> questions;

    public GamesResource(List<Question> questions) {
        this.questions = questions;
        this.games = new ArrayList<Game>();
    }

    public static List<Question> getQuestions() {
        return questions;
    }

    public static void setQuestions(List<Question> questions) {
        GamesResource.questions = questions;
    }

    @GET
    public List<Game> getGames() {
        return games;
    }

    public static void addGame(Game game) {
        games.add(game);
    }

    public static void removeGame(Game game) {
        games.remove(game);
    }

    public static Game getGame(UUID gameId) {
        Game theGame = games.stream()
                .filter( game -> game.getId().equals(gameId))
                .findAny()
                .orElse(null);
        return theGame;
    }


    public static List<Game> getGamesPlayerIsIn(String player) {
        ArrayList<Game> gamesPlayerIsIn = new ArrayList<Game>();
        for (Game game : games ) {
            boolean playerInGame = game.getPlayers().contains(player);
            if (playerInGame) {
                gamesPlayerIsIn.add(game);
            }
        }
        return gamesPlayerIsIn;
    }

}

