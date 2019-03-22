package com.example.demo.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/layout")
@Component
public class LayoutController {

    private static Session session;

    @OnOpen
    public void onOpen(Session session) {
        LayoutController.session = session;
    }

    @OnClose
    public void onClose() {
        LayoutController.session = null;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
    }

    public static void setLayout(String name) throws IOException {
        session.getBasicRemote().sendText(name);
    }
}
