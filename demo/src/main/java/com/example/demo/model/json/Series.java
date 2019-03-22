package com.example.demo.model.json;

import java.util.Collection;
import java.util.LinkedList;

public class Series {
    private String name;
    private String type;
    private Collection<Object> data = new LinkedList<>();
    private String stack;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Collection<Object> getData() {
        return data;
    }

    public String getStack() {
        return stack;
    }

    public Series setName(String name) {
        this.name = name;
        return this;
    }

    public Series setType(String type) {
        this.type = type;
        return this;
    }

    public Series addData(Object data) {
        this.data.add(data);
        return this;
    }

    public Series setStack(String stack) {
        this.stack = stack;
        return this;
    }
}
