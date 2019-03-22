package com.example.demo.controller;

import com.example.demo.websocket.ChartController;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/operator/chart")
public class ChartOperator {

//    private static final String CONNECTION_ERROR_MESSAGE = "数据库连不上，查个毛啊查";
//    private ChartDao chartDao;
//
//    public OperatorController(ChartDao chartDao) throws IOException {
//        this.chartDao = chartDao;
//    }
//
//    @RequestMapping("/search")
//    public String searchChartNames(@RequestParam String keys) {
//        StringBuilder builder = new StringBuilder();
//        try {
//            for (String name : chartDao.searchNamesByKeys(keys)) {
//                builder.append(",").append(name);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return CONNECTION_ERROR_MESSAGE;
//        }
//        return builder.length() > 0 ?
//                builder.substring(1) :
//                "";
//    }

    private static Map<String, Chart> charts = new HashMap<>();
    private static Map<String, Collection<Chart>> index = new HashMap<>();

    private void addChart(String name, String[] labels) throws IOException {
        Chart chart = new Chart().setName(name).setLabels(labels);
        charts.put(name, chart);
        for (String label : labels) {
            Collection<Chart> charts = index.get(label);
            if (charts == null)
                charts = new LinkedList<>();
            charts.add(chart);
            index.put(label, charts);
        }
    }

    private Gson common = new Gson(),
            nullable = new GsonBuilder().serializeNulls().create(),
            simpleChart = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getName().contains("labels") | f.getName().contains("columns");
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).create();

    public ChartOperator() throws IOException {
        addChart("中华人民共和国1999年至2017年直辖市年度GDP", new String[]{"中国", "中华人民共和国", "GDP", "直辖市", "年度"});
        addChart("中华人民共和国2008年至2017年分省年度GDP", new String[]{"中国", "中华人民共和国", "GDP", "分省", "年度"});
        addChart("中华人民共和国2008年至2017年自治区年度GDP", new String[]{"中国", "中华人民共和国", "GDP", "自治区", "年度"});
        addChart("中华人民共和国2017年分省季度GDP", new String[]{"中国", "中华人民共和国", "GDP", "分省", "季度", "2017年"});
        addChart("中华人民共和国2017年直辖市各产业季度GDP", new String[]{"中国", "中华人民共和国", "GDP", "直辖市", "季度", "2017年"});
    }

    @RequestMapping("/search")
    public String searchChartNames(@RequestParam String keys) {
        String[] keysArray = keys.split(" ");
        Collection<Chart> result = null;
        for (String key : keysArray) {
            // 一个关键字对应多个标签
            Collection<String> labels = new LinkedList<>();
            for (String label : index.keySet())
                if (label.contains(key))
                    labels.add(label);

            // 一个标签对应多个值
            Collection<Chart> charts = new HashSet<>();
            for (String label : labels)
                charts.addAll(index.get(label));

            // 与之前结果求交集
            if (result == null) result = charts;
            else result.retainAll(charts);
        }
        return simpleChart.toJson(result);
    }

    @RequestMapping("/types")
    public String searchValidTypes(@RequestParam String name) {
        String[] results = new String[]{"line", "bar", "scatter"};
        return common.toJson(results);
    }

    @RequestMapping("/data")
    public String searchData(@RequestParam String name) {
        return common.toJson(charts.get(name));
    }

    @RequestMapping("/submit")
    public int submitChart(@RequestBody String chart) throws IOException {
        buffer.add(chart);
        ChartController.setChart(buffer.size() - 1, chart);
        return buffer.size() - 1;
    }

    @RequestMapping("/")
    public String all() {
        return nullable.toJson(new Object[]{buffer, positions});
    }

    @RequestMapping("/set")
    public String setPosition(@RequestParam Integer index, @RequestParam(required = false) Integer position) throws IOException {
        if (index >= buffer.size()) return "该图表不存在";
        positions.put(index, position);
        ChartController.setPosition(index, position);
        return "显示设置成功";
    }

    private static List<String> buffer = new ArrayList<>();
    private static BidiMap<Integer, Integer> positions = new DualHashBidiMap<>();

    public static void reset() throws IOException {
        for (int i = 0; i < buffer.size(); i++)
            ChartController.setChart(i, buffer.get(i));
        for (Integer index : positions.keySet())
            ChartController.setPosition(index, positions.get(index));
    }
}

class Chart {
    private String name;
    private String[] labels;
    private List<Column> columns;

    public Chart setName(String name) throws IOException {
        this.name = name;
        this.columns = readColumns(name);
        return this;
    }

    Chart setLabels(String[] labels) {
        this.labels = labels;
        return this;
    }

    public String getName() {
        return name;
    }

    public String[] getLabels() {
        return labels;
    }

    public List<Column> getColumns() {
        return columns;
    }

    private List<Column> readColumns(String tableName) throws IOException {
        File file = new File(tableName + ".csv");
        if (!file.exists()) return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
        String line;
        List<Column> columns = new LinkedList<>();
        while ((line = reader.readLine()) != null) {
            String[] value = line.split(",");
            String name = value[0];
            String type = value[1];
            Object[] data = null;
            switch (type) {
                case "category":
                    data = Arrays.copyOfRange(value, 2, value.length);
                    break;
                case "value":
                    data = new BigDecimal[value.length - 2];
                    for (int i = 0; i < data.length; i++)
                        data[i] = new BigDecimal(value[i + 2]);
                    break;
            }
            columns.add(new Column(name, type, data));
        }
        return columns;
    }
}

class Column {
    private String name;
    private String type;
    private Object[] data;

    Column(String name, String type, Object[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}