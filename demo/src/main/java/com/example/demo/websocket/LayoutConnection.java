package com.example.demo.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;

@ServerEndpoint(value = "/layout")
@Component
public class LayoutConnection {

    private Session session;
    private String layout;
    private static LayoutConnection connection;

    public static LayoutConnection getConnection() {
        return connection;
    }

    public void reset() {
        layout = null;
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

    public boolean setLayout(String layout) throws IOException {
        if (Objects.equals(this.layout, layout)) return false;
        session.getBasicRemote().sendText(this.layout = layout);
        return true;
    }
}
