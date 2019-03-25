package com.example.demo.websocket;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/chart")
@Component
public class ChartConnection {

    private Gson json = new Gson();

    private Session session;
    private static ChartConnection connection;

    public static ChartConnection getConnection() {
        return connection;
    }

    public static void clearConnection() {
        connection = null;
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        connection = this;
        System.out.println("chart connect");
    }

    @OnClose
    public void onClose() {
        connection = null;
        System.out.println("chart disconnect");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
    }

    private static class Command {
        private String cmd;

        public Command(String cmd) {
            this.cmd = cmd;
        }
    }

    private static class Draw extends Command {
        private String chart;
        private int position;

        public Draw(String chart, int position) {
            super("draw");
            this.chart = chart;
            this.position = position;
        }
    }

    public void draw(String chart, int position) throws IOException {
        session.getBasicRemote().sendText(json.toJson(new Draw(chart, position)));
    }

    private static class Clear extends Command {
        private int position;

        public Clear(int position) {
            super("clear");
            this.position = position;
        }
    }

    public void clear(int position) throws IOException {
        session.getBasicRemote().sendText(json.toJson(new Clear(position)));
    }

    private static class Title extends Command {
        private String title;

        Title(String title) {
            super("setTitle");
            this.title = title;
        }
    }

    public void setTitle(String title) throws IOException {
        session.getBasicRemote().sendText(json.toJson(new Title(title)));
    }

    private static class Theme extends Command {
        private String theme;

        public Theme(String theme) {
            super("setTheme");
            this.theme = theme;
        }
    }

    public void setTheme(String theme) throws IOException {
        session.getBasicRemote().sendText(json.toJson(new Theme(theme)));
    }
}
