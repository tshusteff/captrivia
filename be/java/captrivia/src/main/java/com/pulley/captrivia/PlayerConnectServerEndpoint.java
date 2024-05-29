package com.pulley.captrivia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulley.captrivia.model.game.Game;
import com.pulley.captrivia.model.playercommand.PlayerCommand;
import com.pulley.captrivia.model.playercommand.PlayerCommandCreate;
import com.pulley.captrivia.model.playerevent.PlayerEvent;
import com.pulley.captrivia.model.playerevent.PlayerEventConnect;
import com.pulley.captrivia.resources.GamesResource;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@ServerEndpoint("/connect")
@Slf4j
public class PlayerConnectServerEndpoint {
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
            PlayerEventConnect playerEventConnect = new PlayerEventConnect();
            PlayerEvent playerEvent = new PlayerEvent(playerNameParamValue, playerEventConnect);
            ObjectMapper mapper = new ObjectMapper();
            try {
                String playerEventJSONString = mapper.writeValueAsString(playerEvent);
                session.getBasicRemote().sendText(playerEventJSONString); // TODO. How to I reach all players? Not sure.
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
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        log.info("Server: Received Message: {}", message);
//        if (!message.equals("ping")) {
//            throw new IllegalArgumentException("Invalid message received: " + message);
//        }
//        session.getBasicRemote().sendText("pong");
        // convert the message to JSON

        try {
            ObjectMapper mapper = new ObjectMapper();
            PlayerCommand command = mapper.readValue(message, PlayerCommand.class);

            // check at the type
            if (command.getPayload() instanceof PlayerCommandCreate){
                log.info("Server: Message found instance of PlayerCommandCreate");
                PlayerCommandCreate playerCommandCreate = (PlayerCommandCreate) command.getPayload();
                GamesResource.addGame(new Game(UUID.fromString(command.getNonce()), playerCommandCreate.getName(), playerCommandCreate.getQuestion_count()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}