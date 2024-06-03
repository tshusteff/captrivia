package com.pulley.captrivia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulley.captrivia.model.game.Game;
import com.pulley.captrivia.model.gameevent.*;
import com.pulley.captrivia.model.playercommand.*;
import com.pulley.captrivia.model.playerevent.PlayerEvent;
import com.pulley.captrivia.model.playerevent.PlayerEventConnect;
import com.pulley.captrivia.model.playerevent.PlayerEventDisconnect;
import com.pulley.captrivia.model.questions.Question;
import com.pulley.captrivia.resources.GamesResource;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

@ServerEndpoint("/connect")
@Slf4j
public class PlayerConnectServerEndpoint {
    private static List<Session> sessions = new ArrayList<>();
    private final int QUESTION_SECONDS = 10;
    private final int COUNTDOWN_SECONDS = 10;

    private String getPlayerName(Session session) {
        String queryString = session.getQueryString();
        String playerNameParamValue = null;
        Map<String, List<String>> paramMap = session.getRequestParameterMap();
        for (Map.Entry<String, List<String>> entry : paramMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("name")) {
                if (entry.getValue().size() > 0) {
                    playerNameParamValue = entry.getValue().get(0);
                }
            }
        }
        return playerNameParamValue;
    }

    @OnOpen
    public void open(Session session) throws IOException {
        String queryString = session.getQueryString();
        String playerName = getPlayerName(session);
        log.info("Server: Session Open with name "+playerName+".");
        if (playerName == null || playerName.equals("")) {
            log.info("Server: Session Open whoops no name");
            throw new IllegalArgumentException("You must specify a name");
        } else {
            for (Session currSession : sessions) {
                if (getPlayerName(currSession).equals(getPlayerName(session))) {
                    log.info("Server: Session Open whoops repeat name");
                    throw new IllegalArgumentException("User name "+getPlayerName(session)+" already in use");
                }
            }
            log.info("Server: Session Open glad you are here "+playerName);
            // PlayerEventConnect
            PlayerEventConnect playerEventConnect = new PlayerEventConnect();
            PlayerEvent playerEvent = new PlayerEvent(playerName, playerEventConnect);
            sessions.add(session);
            broadcastObject(playerEvent);

        }
    }

    private void broadcastObject(Object object) throws IOException {
        sendObjectToPlayersInOrOutOfGame(object, null, true);
    }
    private void sendObjectToPlayers(Object object, List<String> specifiedPlayersInGame) throws IOException {
        sendObjectToPlayersInOrOutOfGame(object, specifiedPlayersInGame, true);
    }

    private void sendObjectToNonPlayers(Object object, List<String> specifiedPlayersInGame) throws IOException {
        sendObjectToPlayersInOrOutOfGame(object, specifiedPlayersInGame, false);
    }

    // For all players, use playersInGame as null
    // otherwise, send playersInGame as list of players in game
    // sendToActivePlayers = true to send to all players in game
    // sendToActivePlayers = false to send to all players not in game
    private void sendObjectToPlayersInOrOutOfGame(Object object, List<String> specifiedPlayersInGame, boolean sendToActivePlayers)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String JSONString = mapper.writeValueAsString(object);
        for (Session currSession : sessions) {
            if (currSession.isOpen()) {
                if (    specifiedPlayersInGame == null ||
                        ((sendToActivePlayers) && (specifiedPlayersInGame.contains(getPlayerName(currSession)))) ||
                        ((!sendToActivePlayers) && (!specifiedPlayersInGame.contains(getPlayerName(currSession))))
                ) {
                        log.info("Sending Object " + object.toString() + " to player " + getPlayerName(currSession));
                        currSession.getBasicRemote().sendText(JSONString);
                }
            }
        }
        log.info("Sending text "+JSONString);
    }

    @OnClose
    public void close(Session session) throws IOException {
        log.info("Server: Session Close");
        PlayerEventDisconnect playerEventDisconnect = new PlayerEventDisconnect();
        PlayerEvent playerEvent = new PlayerEvent(getPlayerName(session), playerEventDisconnect);
        broadcastObject(playerEvent);

        List<Game> gamesPlayerIsIn = GamesResource.getGamesPlayerIsIn(getPlayerName(session));
        GameEventPlayerLeave gameEventPlayerLeave = new GameEventPlayerLeave(getPlayerName(session));
        for (Game game : gamesPlayerIsIn) {
            GameEvent gameEvent = new GameEvent(game.getId(), gameEventPlayerLeave);
            sendObjectToPlayers(gameEvent, game.getPlayers());
            game.removePlayer(getPlayerName(session));
        }
        sessions.removeIf(currentSession -> currentSession.getId().equals(session.getId()));
    }

    @OnError
    public void onError(Throwable error) {
        log.error("WebSocket Error",error);
        if (error instanceof JsonProcessingException)  {
            log.error("Tried creating new Object json but got error " + error.getMessage());
        }
        if (error instanceof IOException) {
            log.error("Tried sending Object but got error " + error.getMessage());
        }
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        log.info("Server: Received Message: {}", message);
        Set<Session> allSessions = session.getOpenSessions();

        try {
            ObjectMapper mapper = new ObjectMapper();
            PlayerCommand command = mapper.readValue(message, PlayerCommand.class);

            // check at the type
            if (command.getPayload() instanceof PlayerCommandCreate){
                log.info("Server: Message found instance of PlayerCommandCreate");
                PlayerCommandCreate playerCommandCreate = (PlayerCommandCreate) command.getPayload();
                log.info("Server: Message found instance of PlayerCommandCreate. Game with name "+playerCommandCreate.getName()+" and question_count "+playerCommandCreate.getQuestion_count());
                Game newGame = new Game(playerCommandCreate.getName(), playerCommandCreate.getQuestion_count());
                GamesResource.addGame(newGame);
                // GameEventCreate
                GameEventCreate gameEventCreate = new GameEventCreate(playerCommandCreate.getName(), playerCommandCreate.getQuestion_count());
                GameEvent gameEvent = new GameEvent(newGame.getId(), gameEventCreate);
                broadcastObject(gameEvent);
            }
            if (command.getPayload() instanceof PlayerCommandJoin) {
                PlayerCommandJoin playerCommandJoin = (PlayerCommandJoin) command.getPayload();
                Game game = GamesResource.getGame(playerCommandJoin.getGame_id());
                int playerCount = game.addPlayer(getPlayerName(session));
                GameEventPlayerCount gameEventPlayerCount = new GameEventPlayerCount(playerCount);
                GameEvent gameEvent = new GameEvent(playerCommandJoin.getGame_id(), gameEventPlayerCount);
                broadcastObject(gameEvent);

                GameEventPlayerEnter gameEventPlayerEnter = new GameEventPlayerEnter(
                        getPlayerName(session),
                        game.getPlayers(),
                        game.getPlayersReady(),
                        game.getQuestion_count());
                gameEvent = new GameEvent(playerCommandJoin.getGame_id(), gameEventPlayerEnter);
                // only send to player who joined game.
                sendObjectToPlayers(gameEvent, Arrays.asList(getPlayerName(session)));

                GameEventPlayerJoin gameEventPlayerJoin = new GameEventPlayerJoin(getPlayerName(session));
                gameEvent = new GameEvent(playerCommandJoin.getGame_id(), gameEventPlayerJoin);
                sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandJoin.getGame_id()).getPlayers());
            }

            if (command.getPayload() instanceof PlayerCommandReady) {
                PlayerCommandReady playerCommandReady = (PlayerCommandReady) command.getPayload();
                Game game = GamesResource.getGame(playerCommandReady.getGame_id());
                game.setPlayerReady(getPlayerName(session));
                GameEventPlayerReady gameEventPlayerReady = new GameEventPlayerReady(getPlayerName(session));
                GameEvent gameEvent = new GameEvent(playerCommandReady.getGame_id(), gameEventPlayerReady);
                sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandReady.getGame_id()).getPlayers());

            }

            if (command.getPayload() instanceof PlayerCommandStart) {
                PlayerCommandStart playerCommandStart = (PlayerCommandStart) command.getPayload();
                Game game = GamesResource.getGame(playerCommandStart.getGame_id());
                GameEventStart gameEventStart = new GameEventStart();
                GameEvent gameEvent = new GameEvent(game.getId(), gameEventStart);
                sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandStart.getGame_id()).getPlayers());

                game.setState("start");
                GameEventStateChange gameEventStateChange = new GameEventStateChange("start");
                gameEvent = new GameEvent(playerCommandStart.getGame_id(), gameEventStateChange);
                sendObjectToNonPlayers(gameEvent, GamesResource.getGame(playerCommandStart.getGame_id()).getPlayers());

                // Ask first question
                if (game.getQuestion_count() > 0 ) {
                    askQuestion(0, game);
                } else {
                    // no questions to ask. Front end prohibits.
                }

            }

            if (command.getPayload() instanceof PlayerCommandAnswer) {
                PlayerCommandAnswer playerCommandAnswer = (PlayerCommandAnswer) command.getPayload();
                Game game = GamesResource.getGame(playerCommandAnswer.getGame_id());
                UUID questionId = playerCommandAnswer.getQuestion_id(); // TODO don't actually know how this maps to a question
                int currentQuestionIndex = game.getCurrentQuestionIndex();
                Question question = GamesResource.getQuestions().get(currentQuestionIndex);
                if (question.getCorrectIndex() == playerCommandAnswer.getIndex()) {
                    GameEventPlayerCorrect gameEventPlayerCorrect = new GameEventPlayerCorrect(game.getId(), getPlayerName(session));
                    GameEvent gameEvent = new GameEvent(game.getId(), gameEventPlayerCorrect);
                    sendObjectToPlayers(gameEvent, game.getPlayers());
                    game.addPointForPlayer(getPlayerName(session));
                } else {
                    GameEventPlayerIncorrect gameEventPlayerIncorrect = new GameEventPlayerIncorrect(game.getId(), getPlayerName(session));
                    GameEvent gameEvent = new GameEvent(game.getId(), gameEventPlayerIncorrect);
                    sendObjectToPlayers(gameEvent, game.getPlayers());
                }

                GameEventCountdown gameEventCountdown = new GameEventCountdown(5);
                GameEvent gameEvent = new GameEvent(game.getId(), gameEventCountdown);
                sendObjectToPlayers(gameEvent, GamesResource.getGame(game.getId()).getPlayers());

                // ask another question
                currentQuestionIndex++;
                game.setCurrentQuestionIndex(currentQuestionIndex);
                if (currentQuestionIndex < game.getQuestion_count()) {
                    askQuestion(currentQuestionIndex, game);
                } else {
                    // we've asked all the questions. game over
                    List<PlayerScore> playerScores = game.getPlayerScores();
                    log.info("Player Scores is "+playerScores);
                    GameEventEnd gameEventEnd = new GameEventEnd(playerScores);
                    gameEvent = new GameEvent(game.getId(), gameEventEnd);
                    sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandAnswer.getGame_id()).getPlayers());

                    // TODO: here is where I could update the Leaderboard

                    GameEventDestroy gameEventDestroy = new GameEventDestroy();
                    gameEvent = new GameEvent(game.getId(), gameEventDestroy);
                    sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandAnswer.getGame_id()).getPlayers());
                    // remove the game
                    GamesResource.removeGame(game);
                }
            }
        } catch (Exception e) {
            log.error("Got exception " + e);
        }
    }

    private void askQuestion(int questionIndex, Game game) throws IOException {
        GameEventQuestion gameEventQuestion = new GameEventQuestion(
                game.getId(),
                GamesResource.getQuestions().get(questionIndex).getOptions(),
                GamesResource.getQuestions().get(questionIndex).getQuestionText(),
                QUESTION_SECONDS
        );
        GameEvent gameEvent = new GameEvent(game.getId(), gameEventQuestion);
        sendObjectToPlayers(gameEvent, game.getPlayers());
    }

}