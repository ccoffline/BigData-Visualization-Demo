package com.example.demo.model.json;

import java.util.Collection;
import java.util.LinkedList;

public class ChartBuilder {

    private Title title;
    private Legend legend;
    private Collection<Axis> xAxis = new LinkedList<>();
    private Collection<Axis> yAxis = new LinkedList<>();
    private Collection<Series> data = new LinkedList<>();

    public Title getTitle() {
        return title;
    }

    public Legend getLegend() {

        return legend;
    }

    public Collection<Axis> getXAxis() {
        return xAxis;
    }

    public Collection<Axis> getYAxis() {
        return yAxis;
    }

    public Collection<Series> getData() {
        return data;
    }

    public ChartBuilder setTitle(Title title) {
        this.title = title;
        return this;
    }

    public ChartBuilder setLegend(Legend legend) {
        this.legend = legend;
        return this;
    }

    public ChartBuilder addXAxis(Axis xAxis) {
        this.xAxis.add(xAxis);
        return this;
    }

    public ChartBuilder addYAxis(Axis yAxis) {
        this.yAxis.add(yAxis);
        return this;
    }

    public ChartBuilder addSeries(Series series) {
        this.data.add(series);
        return this;
    }
}
