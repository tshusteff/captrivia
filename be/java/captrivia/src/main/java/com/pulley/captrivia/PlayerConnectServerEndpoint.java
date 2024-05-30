package com.pulley.captrivia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulley.captrivia.model.game.Game;
import com.pulley.captrivia.model.gameevent.*;
import com.pulley.captrivia.model.playercommand.*;
import com.pulley.captrivia.model.playerevent.PlayerEvent;
import com.pulley.captrivia.model.playerevent.PlayerEventConnect;
import com.pulley.captrivia.model.playerevent.PlayerEventDisconnect;
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
                    log.info("Server: Session Open welcome playerNameParamValue "+playerNameParamValue);
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

    // TODO do something with exceptions.
    private void broadcastObject(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String JSONString = mapper.writeValueAsString(object);
            for (Session currSession : sessions) {
                log.info("broadcastPlayerEvent: List of sessions, session with id "+currSession.getId()+"and queryString "+currSession.getQueryString());
                if (currSession.isOpen()) {
                    currSession.getBasicRemote().sendText(JSONString);
                }
            }
            log.info("Tried sending text "+JSONString);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Tried creating new Object json but got error " + jsonProcessingException.getMessage());
        } catch (IOException ioException) {
            log.error("Tried sending Object but got error " + ioException);
        }
    }
    private void sendObjectToPlayers(Object object, List<String> players) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String JSONString = mapper.writeValueAsString(object);
            for (Session currSession : sessions) {
                if (currSession.isOpen()) {
                    if (players.contains(getPlayerName(currSession))) {
                        log.error("Sending Object "+object.toString()+" to player "+getPlayerName(currSession));
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

        // TODO remove this player from all the games they played
        // do I also need to remove the games owned by this player?
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
                broadcastObject(gameEvent);
            }

            if (command.getPayload() instanceof PlayerCommandStart) {
                PlayerCommandStart playerCommandStart = (PlayerCommandStart) command.getPayload();
                GameEventStart gameEventStart = new GameEventStart();
                GameEvent gameEvent = new GameEvent(playerCommandStart.getGame_id(), gameEventStart);
                broadcastObject(gameEvent);
            }
        } catch (Exception e) {
            log.error("Got exception " + e);
        }
    }

}