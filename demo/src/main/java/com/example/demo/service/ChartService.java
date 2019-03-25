package com.example.demo.service;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ChartService {

    private Map<String, Chart> charts = new HashMap<>();
    private Map<String, Collection<Chart>> index = new HashMap<>();

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

    public ChartService() throws IOException {
        addChart("1999年至2017年直辖市年度GDP", new String[]{"GDP", "直辖市", "年度", "2017", "1999"});
        addChart("2008年至2017年部分省市年度GDP", new String[]{"GDP", "年度", "2017", "2008"});
        addChart("2009年至2017年部分省市第三产业增加值", new String[]{"第三产业", "增加值", "年度", "2017", "2009"});
        addChart("2009年至2017年部分省市建筑业增加值", new String[]{"建筑业", "增加值", "年度", "2017", "2009"});
        addChart("2009年至2017年部分省市批发和零售业增加值", new String[]{"批发和零售业", "增加值", "年度", "2017", "2009"});
        addChart("2009年至2017年部分省市住宿和餐饮业增加值", new String[]{"住宿和餐饮业", "增加值", "年度", "2017", "2009"});
    }

    private Gson json = new Gson(),
            nullableJson = new GsonBuilder().serializeNulls().create(),
            simpleChartJson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getName().contains("labels") | f.getName().contains("columns");
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).create();

    public String searchNamesByKeys(String keys) {
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
        return simpleChartJson.toJson(result);
    }

    public String getDataByName(String name) {
        return json.toJson(charts.get(name));
    }

    private static class Chart {
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

    private static class Column {
        private String name;
        private String type;
        private Object[] data;

        Column(String name, String type, Object[] data) {
            this.name = name;
            this.type = type;
            this.data = data;
        }
    }
}
