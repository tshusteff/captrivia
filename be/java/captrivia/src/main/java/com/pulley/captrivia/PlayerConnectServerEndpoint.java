package com.pulley.captrivia;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@ServerEndpoint("/connect")
@Slf4j
public class PlayerConnectServerEndpoint {
    @OnOpen
    public void open(Session session) {
        log.info("Server: Session Open");
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