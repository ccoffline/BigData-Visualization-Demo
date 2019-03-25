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
public class LayoutConnection {

    private Session session;
    private static LayoutConnection connection;

    public static LayoutConnection getConnection() {
        return connection;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        connection = this;
        System.out.println("layout connect");
    }

    @OnClose
    public void onClose() {
        connection = null;
        System.out.println("layout disconnect");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
    }

    public void setLayout(String layout) throws IOException {
        session.getBasicRemote().sendText(layout);
    }
}
