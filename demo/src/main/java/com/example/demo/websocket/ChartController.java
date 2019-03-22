package com.example.demo.websocket;

import com.example.demo.controller.ChartOperator;
import com.example.demo.controller.LayoutOperator;
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
public class ChartController {

    private static Session session;

    private static Gson common = new Gson();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        ChartController.session = session;
        ChartOperator.reset();
        LayoutOperator.reset();
    }

    @OnClose
    public void onClose() {
        ChartController.session = null;
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

    private static class Chart extends Command {
        private int index;
        private String chart;

        Chart(int index, String chart) {
            super("setChart");
            this.index = index;
            this.chart = chart;
        }
    }

    public static void setChart(int index, String chart) throws IOException {
        session.getBasicRemote().sendText(common.toJson(new Chart(index, chart)));
    }

    private static class Position extends Command {
        private int index;
        private int position;

        Position(int index, int position) {
            super("setPosition");
            this.index = index;
            this.position = position;
        }
    }

    public static void setPosition(int index, int position) throws IOException {
        session.getBasicRemote().sendText(common.toJson(new Position(index, position)));
    }

    private static class Title extends Command {
        private String title;

        Title(String title) {
            super("setTitle");
            this.title = title;
        }
    }

    public static void setTitle(String title) throws IOException {
        session.getBasicRemote().sendText(common.toJson(new Title(title)));
    }
}
