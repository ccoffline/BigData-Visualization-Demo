package com.example.demo.model.charts;

import com.example.demo.model.field.Field;
import com.example.demo.model.json.ChartBuilder;
import com.example.demo.util.ClassUtil;
import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class Chart {

    private String name;

    public String getName() {
        return name;
    }

    public Chart setName(String name) {
        this.name = name;
        return this;
    }

    private ChartBuilder builder;

    public ChartBuilder getBuilder() {
        return builder;
    }

    public Chart setBuilder(ChartBuilder builder) {
        this.builder = builder;
        return this;
    }

    public abstract Chart setStructure(String location, String name);

    public abstract void addField(Field field);

    @Override
    public String toString() {
        return new Gson().toJson(builder);
    }

    private static Map<String, Class<? extends Chart>> chartsMap = new HashMap<>();

    static {
        for (Class<? extends Chart> chart : ClassUtil.getSubClasses(Chart.class)) {
            if (!Modifier.isAbstract(chart.getModifiers()))
                chartsMap.put(chart.getSimpleName().toLowerCase(), chart);
            System.out.println(chart.getSimpleName());
        }
    }

    public static Chart generateChart(String name, String type) {
        try {
            Chart chart = chartsMap.get(type).getConstructor().newInstance();
            chart.setName(name);
            return chart;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
