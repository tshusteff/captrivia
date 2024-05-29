package com.pulley.captrivia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulley.captrivia.model.game.Game;
import com.pulley.captrivia.model.gameevent.GameEvent;
import com.pulley.captrivia.model.gameevent.GameEventCreate;
import com.pulley.captrivia.model.gameevent.GameEventPlayerCount;
import com.pulley.captrivia.model.playercommand.PlayerCommand;
import com.pulley.captrivia.model.playercommand.PlayerCommandCreate;
import com.pulley.captrivia.model.playercommand.PlayerCommandJoin;
import com.pulley.captrivia.model.playerevent.PlayerEvent;
import com.pulley.captrivia.model.playerevent.PlayerEventConnect;
import com.pulley.captrivia.model.playerevent.PlayerEventDisconnect;
import com.pulley.captrivia.resources.GamesResource;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


@ServerEndpoint("/connect")
@Slf4j
public class PlayerConnectServerEndpoint {
    private String playerName;
    @OnOpen
    public void open(Session session) throws JsonProcessingException {
        String queryString = session.getQueryString();
        String playerNameParamValue = "";
        log.info("Server: Session Open with name "+queryString);
        Map<String, List<String>> paramMap = session.getRequestParameterMap();
        for (Map.Entry<String, List<String>> entry : paramMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("name")) {
                if (entry.getValue().size() > 0) {
                    playerNameParamValue = entry.getValue().get(0);
                    log.info("Server: Session Open welcome playerNameParamValue "+playerNameParamValue);
                }
            }
        }
        if ("".equals(playerNameParamValue)) {
            log.info("Server: Session Open whoops no name");
        } else {
            log.info("Server: Session Open glad you are here "+playerNameParamValue);
            playerName = playerNameParamValue;
            // PlayerEventConnect
            PlayerEventConnect playerEventConnect = new PlayerEventConnect();
            PlayerEvent playerEvent = new PlayerEvent(playerNameParamValue, playerEventConnect);
            ObjectMapper mapper = new ObjectMapper();
            try {
                String playerEventJSONString = mapper.writeValueAsString(playerEvent);
                session.getBasicRemote().sendText(playerEventJSONString); // TODO. How to I reach all players? Not sure.
                log.error("Tried sending text "+playerEventJSONString);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error("Tried creating new PlayerEvent json but got error " + jsonProcessingException.getMessage());
            } catch (IOException ioException) {
                log.error("Tried sending playerEvent but got error " + ioException);
            }
        }
    }

    @OnClose
    public void close(Session session) {
        log.info("Server: Session Close");
        PlayerEventDisconnect playerEventDisconnect = new PlayerEventDisconnect();
        PlayerEvent playerEvent = new PlayerEvent(playerName, playerEventDisconnect);
        // TODO go through the list of games and remove this player from them
        ObjectMapper mapper = new ObjectMapper();
        try {
            String playerEventJSONString = mapper.writeValueAsString(playerEvent);
            session.getBasicRemote().sendText(playerEventJSONString); // TODO. How to I reach all players? Not sure.
            log.error("Tried sending text "+playerEventJSONString);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Tried creating new PlayerEvent json but got error " + jsonProcessingException.getMessage());
        } catch (IOException ioException) {
            log.error("Tried sending playerEvent but got error " + ioException);
        }

    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        log.info("Server: Received Message: {}", message);
        Set<Session> allSessions = session.getOpenSessions();
        for (Session currSession : allSessions) {
            try {
                currSession.getBasicRemote().sendText("session with query string " + currSession.getQueryString());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }

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
                Game newGame = new Game(playerCommandCreate.getName(), playerCommandCreate.getQuestion_count());
                GamesResource.addGame(newGame);
                // GameEventCreate
                GameEventCreate gameEventCreate = new GameEventCreate();
                GameEvent gameEvent = new GameEvent(newGame.getId(), gameEventCreate);
                try {
                    String gameEventJSONString = mapper.writeValueAsString(gameEvent);
                    session.getBasicRemote().sendText(gameEventJSONString);
                    log.error("Tried sending text "+gameEventJSONString);
                } catch (JsonProcessingException jsonProcessingException) {
                    log.error("Tried creating new GameEvent json but got error " + jsonProcessingException.getMessage());
                } catch (IOException ioException) {
                    log.error("Tried sending GameEvent but got error " + ioException);
                }
            }
            if (command.getPayload() instanceof PlayerCommandJoin) {
                PlayerCommandJoin playerCommandJoin = (PlayerCommandJoin) command.getPayload();
                log.info("Exciting. New PLayer joins game " + playerCommandJoin.getGame_id());
                int playerCount = GamesResource.addPlayerToGame(playerCommandJoin.getGame_id(), playerName);
                GameEventPlayerCount gameEventPlayerCount = new GameEventPlayerCount(playerCount);
                GameEvent gameEvent = new GameEvent(playerCommandJoin.getGame_id(), gameEventPlayerCount);
                try {
                    String gameEventJSONString = mapper.writeValueAsString(gameEvent);
                    session.getBasicRemote().sendText(gameEventJSONString);
                    log.error("Tried sending text " + gameEventJSONString);
                } catch (JsonProcessingException jsonProcessingException) {
                    log.error("Tried creating new GameEvent json but got error " + jsonProcessingException.getMessage());
                } catch (IOException ioException) {
                    log.error("Tried sending GameEvent but got error " + ioException);
                }

            }
        } catch (Exception e) {
            log.error("Got exception " + e);
        }
    }

}