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

    // returns playercount
    public static int addPlayerToGame(UUID gameId, String player) {
        Game theGame = getGame(gameId);
        if (theGame != null) {
            log.error("Adding player "+player+" to game with gameId "+gameId);
            theGame.addPlayer(player);
            return theGame.getPlayer_count();
        } else {
            log.error("Did not find game with gameId "+gameId);
            return 0;
        }
    }

    public static void removePlayerFromAllGames(String player) {
        for (Game game : games) {
            boolean playerInGame = game.getPlayers().remove(player);
            if (playerInGame) {
                game.setPlayer_count(game.getPlayer_count()-1);
            }
        }
    }

}

