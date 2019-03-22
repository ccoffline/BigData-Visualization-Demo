package com.example.demo.model.json;

public class Axis {

    private String name;
    private String type;
    private boolean scale;
    private boolean boundaryGap;
    private Object[] data;
    private AxisLabel axisLabel;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isScale() {
        return scale;
    }

    public boolean isBoundaryGap() {
        return boundaryGap;
    }

    public Object[] getData() {
        return data;
    }

    public AxisLabel getAxisLabel() {
        return axisLabel;
    }

    public Axis setName(String name) {
        this.name = name;
        return this;
    }

    public Axis setType(String type) {
        this.type = type;
        return this;
    }

    public Axis setScale(boolean scale) {
        this.scale = scale;
        return this;
    }

    public Axis setBoundaryGap(boolean boundaryGap) {
        this.boundaryGap = boundaryGap;
        return this;
    }

    public Axis setData(Object[] data) {
        this.data = data;
        return this;
    }

    public Axis setAxisLabel(AxisLabel axisLabel) {
        this.axisLabel = axisLabel;
        return this;
    }
}
