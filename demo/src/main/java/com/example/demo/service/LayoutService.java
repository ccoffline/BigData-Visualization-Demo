package com.example.demo.service;

import com.example.demo.websocket.ChartConnection;
import com.example.demo.websocket.LayoutConnection;
import com.google.gson.Gson;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LayoutService {

    private Gson json = new Gson();

    private List<Layout> buffer = new ArrayList<>();
    private Integer selected;

    public String getLayouts() {
        return json.toJson(buffer);
    }

    public void addLayout(String name, String title, String theme) {
        buffer.add(new Layout(name, title, theme));
    }

    public void selectLayout(Integer select) throws IOException {
        this.selected = select;
        if (!LayoutConnection.getConnection().setLayout(buffer.get(select).getName())) return;
        ChartConnection.clearConnection();
        try {
            while (ChartConnection.getConnection() == null)
                Thread.sleep(200);
        } catch (InterruptedException ignored) {
        }
        Layout layout = buffer.get(selected);
        ChartConnection.getConnection().setTitle(layout.getTitle());
        ChartConnection.getConnection().setTheme(layout.getTheme());
        for (Integer position : layout.getPositions().values())
            ChartConnection.getConnection().draw(charts.get(layout.getPositions().getKey(position)), position);
    }

    public void clear(Integer index) throws IOException {
        if (Objects.equals(index, selected))
            for (int position : buffer.get(selected).getPositions().values())
                ChartConnection.getConnection().clear(position);
        buffer.get(index).clear();
    }

    private List<String> charts = new ArrayList<>();

    public void addChart(String chart) {
        charts.add(chart);
    }

    public String getChartsInSelectedLayout() {
        Object positions = new Object();
        if (selected != null) positions = buffer.get(selected).getPositions();
        return json.toJson(new Object[]{charts, positions});
    }

    public String display(Integer index, Integer position) throws IOException {
        if (selected == null) return "未选中布局";
        if (index >= charts.size()) return "该图表不存在";
        if (ChartConnection.getConnection() == null) return "未连接显示屏";
        Integer oldPosition = buffer.get(selected).getPositions().put(index, position);
        String chart = charts.get(index);
        if (!Objects.equals(oldPosition, position)) {
            if (position != null)
                ChartConnection.getConnection().draw(chart, position);
            if (oldPosition != null)
                ChartConnection.getConnection().clear(oldPosition);
        }
        return "设置成功";
    }

    private static class Layout {

        private BidiMap<Integer, Integer> positions = new DualHashBidiMap<>();

        public BidiMap<Integer, Integer> getPositions() {
            return positions;
        }

        public void clear() {
            positions = new DualHashBidiMap<>();
        }

        private String name;
        private String title;
        private String theme;

        public Layout(String name, String title, String theme) {
            this.name = name;
            this.title = title;
            this.theme = theme;
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }

        public String getTheme() {
            return theme;
        }
    }
}
