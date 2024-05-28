package com.pulley.captrivia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulley.captrivia.model.game.Game;
import com.pulley.captrivia.model.playercommand.PlayerCommand;
import com.pulley.captrivia.model.playercommand.PlayerCommandCreate;
import com.pulley.captrivia.model.playercommand.PlayerCommandType;
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
    public void open(Session session) {
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

        log.info("Server: Received Message: 0");
        try {
            log.info("Server: Received Message: 1");
            ObjectMapper mapper = new ObjectMapper();
            log.info("Server: Received Message: 2");
            PlayerCommand command = mapper.readValue(message, PlayerCommand.class);
            log.info("Server: Received Message: 3");

            // check at the type
            log.info("Server: Message nonce is " + command.getNonce());
            log.info("Server: Message payload is " + command.getPayload());
            log.info("Server: Message payload class is " + command.getPayload().getClass());

            log.info("Server: Message class is " + command.getClass());
//            if (command.getPayload().getClass() instanceof PlayerCommandCreate){
                PlayerCommandCreate playerCommandCreate = (PlayerCommandCreate) command.getPayload();
                GamesResource.addGame(new Game(UUID.fromString(command.getNonce()), "Game1", 3));
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}