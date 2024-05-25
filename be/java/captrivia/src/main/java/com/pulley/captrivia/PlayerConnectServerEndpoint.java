package com.pulley.captrivia;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;


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
        if (!message.equals("ping")) {
            throw new IllegalArgumentException("Invalid message received: " + message);
        }
        session.getBasicRemote().sendText("pong");
    }
}