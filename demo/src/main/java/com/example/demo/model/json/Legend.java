package com.example.demo.model.json;

import java.util.*;

public class Legend {

    private Collection<String> data = new HashSet<>();

    public Collection<String> getData() {
        return data;
    }

    public Legend addData(String data) {
        this.data.add(data);
        return this;
    }
}
