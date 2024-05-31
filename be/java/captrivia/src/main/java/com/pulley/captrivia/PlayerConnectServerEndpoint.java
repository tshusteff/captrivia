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
    public void open(Session session) throws JsonProcessingException {
        String queryString = session.getQueryString();
        String playerName = getPlayerName(session);
        log.info("Server: Session Open with name "+playerName+".");
        log.info("Server: Session Open welcome playerNameParamValue "+playerName);
        if (playerName == null) {
            log.info("Server: Session Open whoops no name");
        } else {
            log.info("Server: Session Open glad you are here "+playerName);
            // PlayerEventConnect
            PlayerEventConnect playerEventConnect = new PlayerEventConnect();
            PlayerEvent playerEvent = new PlayerEvent(playerName, playerEventConnect);
            sessions.add(session);
            broadcastObject(playerEvent);
        }
    }

    private void broadcastObject(Object object) {
        sendObjectToPlayersInOrOutOfGame(object, null, true);
    }
    private void sendObjectToPlayers(Object object, List<String> specifiedPlayersInGame) {
        sendObjectToPlayersInOrOutOfGame(object, specifiedPlayersInGame, true);
    }

    private void sendObjectToNonPlayers(Object object, List<String> specifiedPlayersInGame) {
        sendObjectToPlayersInOrOutOfGame(object, specifiedPlayersInGame, false);
    }

    // TODO do something with exceptions.
    // for all players, use playersInGame as null
    // otherwise, send playersInGame as list of players in game
    // sendToActivePlayers = true to send to all players in game
    // sendToActivePlayers = false to send to all players not in game
    private void sendObjectToPlayersInOrOutOfGame(Object object, List<String> specifiedPlayersInGame, boolean sendToActivePlayers) {
        ObjectMapper mapper = new ObjectMapper();
        try {
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
            log.info("Tried sending text "+JSONString);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Tried creating new Object json but got error " + jsonProcessingException.getMessage());
        } catch (IOException ioException) {
            log.error("Tried sending Object but got error " + ioException);
        }
    }

    @OnClose
    public void close(Session session) {
        log.info("Server: Session Close");
        PlayerEventDisconnect playerEventDisconnect = new PlayerEventDisconnect();
        PlayerEvent playerEvent = new PlayerEvent(getPlayerName(session), playerEventDisconnect);
        broadcastObject(playerEvent);

        GamesResource.removePlayerFromAllGames(getPlayerName(session));
        // TODO do I also need to remove the games owned by this player?
        sessions.removeIf(currentSession -> currentSession.getId().equals(session.getId()));
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        log.info("Server: Received Message: {}", message);
        Set<Session> allSessions = session.getOpenSessions();

//        if (!message.equals("ping")) {
//            throw new IllegalArgumentException("Invalid message received: " + message);
//        }

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
                int playerCount = GamesResource.addPlayerToGame(playerCommandJoin.getGame_id(), getPlayerName(session));
                GameEventPlayerCount gameEventPlayerCount = new GameEventPlayerCount(playerCount);
                GameEvent gameEvent = new GameEvent(playerCommandJoin.getGame_id(), gameEventPlayerCount);
                broadcastObject(gameEvent);

                GameEventPlayerEnter gameEventPlayerEnter = new GameEventPlayerEnter(
                        getPlayerName(session),
                        GamesResource.getGame(playerCommandJoin.getGame_id()).getPlayers(),
                        new HashMap<String, Boolean>(),
                        GamesResource.getGame(playerCommandJoin.getGame_id()).getQuestion_count());
                gameEvent = new GameEvent(playerCommandJoin.getGame_id(), gameEventPlayerEnter);
                // only send to player who joined game.
                sendObjectToPlayers(gameEvent, Arrays.asList(getPlayerName(session)));

                GameEventPlayerJoin gameEventPlayerJoin = new GameEventPlayerJoin(getPlayerName(session));
                gameEvent = new GameEvent(playerCommandJoin.getGame_id(), gameEventPlayerJoin);
                sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandJoin.getGame_id()).getPlayers());
            }

            if (command.getPayload() instanceof PlayerCommandReady) {
                PlayerCommandReady playerCommandReady = (PlayerCommandReady) command.getPayload();
                GameEventPlayerReady gameEventPlayerReady = new GameEventPlayerReady(getPlayerName(session));
                GameEvent gameEvent = new GameEvent(playerCommandReady.getGame_id(), gameEventPlayerReady);
                sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandReady.getGame_id()).getPlayers());

            }

            if (command.getPayload() instanceof PlayerCommandStart) {
                PlayerCommandStart playerCommandStart = (PlayerCommandStart) command.getPayload();
                GameEventStart gameEventStart = new GameEventStart();
                GameEvent gameEvent = new GameEvent(playerCommandStart.getGame_id(), gameEventStart);
                sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandStart.getGame_id()).getPlayers());

                GameEventStateChange gameEventStateChange = new GameEventStateChange("countdown");
                gameEvent = new GameEvent(playerCommandStart.getGame_id(), gameEventStateChange);
                sendObjectToNonPlayers(gameEvent, GamesResource.getGame(playerCommandStart.getGame_id()).getPlayers());

//                GameEventCountdown gameEventCountdown = new GameEventCountdown(5);
//                gameEvent = new GameEvent(playerCommandStart.getGame_id(), gameEventCountdown);
//                sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandStart.getGame_id()).getPlayers());

                Game game = GamesResource.getGame(playerCommandStart.getGame_id());
                if (game.getQuestion_count() > 0 ) {
                    GameEventQuestion gameEventQuestion = new GameEventQuestion(
                            playerCommandStart.getGame_id(),
                            GamesResource.getQuestions().get(0).getOptions(),
                            GamesResource.getQuestions().get(0).getQuestionText(),
                            10
                    );
                    gameEvent = new GameEvent(playerCommandStart.getGame_id(), gameEventQuestion);
                    sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandStart.getGame_id()).getPlayers());
                } else {
                    // no questions to ask.
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

                // ask another question
                if (currentQuestionIndex < game.getQuestion_count()) {
                    GameEventQuestion gameEventQuestion = new GameEventQuestion(
                            playerCommandAnswer.getGame_id(),
                            GamesResource.getQuestions().get(currentQuestionIndex).getOptions(),
                            GamesResource.getQuestions().get(currentQuestionIndex).getQuestionText(),
                            10
                    );
                    GameEvent gameEvent = new GameEvent(playerCommandAnswer.getGame_id(), gameEventQuestion);
                    sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandAnswer.getGame_id()).getPlayers());
                    game.setCurrentQuestionIndex(currentQuestionIndex + 1);
                } else {
                    // we've asked all the questions. game over
                    GameEventEnd gameEventEnd = new GameEventEnd(game.getPlayerScores());
                    GameEvent gameEvent = new GameEvent(game.getId(), gameEventEnd);
                    sendObjectToPlayers(gameEvent, GamesResource.getGame(playerCommandAnswer.getGame_id()).getPlayers());
                }
            }
        } catch (Exception e) {
            log.error("Got exception " + e);
        }
    }

}